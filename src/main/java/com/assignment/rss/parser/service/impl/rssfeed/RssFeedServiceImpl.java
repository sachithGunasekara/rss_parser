package com.assignment.rss.parser.service.impl.rssfeed;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.assignment.rss.parser.dto.rssfeed.XmlChannelDTO;
import com.assignment.rss.parser.dto.rssfeed.XmlChannelItemDTO;
import com.assignment.rss.parser.model.Channel;
import com.assignment.rss.parser.model.Feed;
import com.assignment.rss.parser.model.Item;
import com.assignment.rss.parser.service.channel.ChannelService;
import com.assignment.rss.parser.service.feed.FeedService;
import com.assignment.rss.parser.service.item.ItemService;
import com.assignment.rss.parser.service.rssfeed.RssFeedService;

/**
 * The Class RssFeedServiceImpl.
 *
 * @author sachi
 */
@Service
public class RssFeedServiceImpl implements RssFeedService {

	private Logger logger = LoggerFactory.getLogger(RssFeedServiceImpl.class);

	/** The rss feed sources. */
	@Value("#{'${rss.feed.sources}'.split(',')}")
	private List<String> rssFeedSources;

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	/** The channel service. */
	@Autowired
	private ChannelService channelService;

	/** The feed service. */
	@Autowired
	private FeedService feedService;

	/** The item service. */
	@Autowired
	private ItemService itemService;

	/**
	 * Gets the rss feed XML string.
	 *
	 * @param rssFeedURL the rss feed URL
	 * @return the rss feed XML string
	 */
	@Override
	public String getRssFeedXMLString(String rssFeedURL) {
		logger.info(String.format("rss feed url - %s", rssFeedURL));
		return restTemplate.getForObject(rssFeedURL, String.class);
	}

	/**
	 * Parses the XML string.
	 *
	 * @param rssFeedXml the rss feed xml
	 * @return the xml channel DTO
	 */
	@Override
	public XmlChannelDTO parseXMLString(String rssFeedXml) {
		logger.info("Parsing XML string");
		
		XmlChannelDTO dto = null;
		XMLStreamReader xsr = null;

		try {
			XMLInputFactory xif = XMLInputFactory.newFactory();
			StreamSource xml = new StreamSource(new ByteArrayInputStream(rssFeedXml.getBytes(StandardCharsets.UTF_8)));
			xsr = xif.createXMLStreamReader(xml);

			while (xsr.hasNext()) {
				xsr.nextTag();
				if (xsr.getLocalName().equals("channel")) {
					break;
				}
			}

			JAXBContext jaxbContext = JAXBContext.newInstance(XmlChannelDTO.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			dto = (XmlChannelDTO) jaxbUnmarshaller.unmarshal(xsr);
		} catch (Exception ex) {
			logger.error("an exception occurred", ex);
		} finally {
			try {
				xsr.close();
			} catch (XMLStreamException ex) {
				logger.error("an exception occurred", ex);
			}
		}
		return dto;
	}

	/**
	 * Convert rss feed to domain model.
	 *
	 * @param dto the dto
	 * @return the channel
	 * @throws ParseException the parse exception
	 */
	@Override
	public Channel convertRssFeedToDomainModel(XmlChannelDTO dto) throws ParseException {
		logger.info("Converting XML string into domain model");
		
		Channel channel = new Channel();
		channel.setDescription(dto.getDescription());
		channel.setLink(dto.getLink());
		channel.setTitle(dto.getTitle());

		Feed feed = new Feed();
		feed.setChannel(channel);

		DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date lasteBuildDate = df.parse(dto.getLastBuildDate());
		feed.setLastBuildDate(lasteBuildDate);

		List<Item> itemList = new ArrayList<>();
		for (XmlChannelItemDTO channelItem : dto.getItem()) {
			Item item = new Item();
			item.setDescription(channelItem.getDescription());
			item.setFeed(feed);
			item.setGuid(channelItem.getGuid());
			item.setTitle(channelItem.getTitle());
			Date publishedDate = df.parse(channelItem.getPubDate());
			item.setPubDate(publishedDate);
			itemList.add(item);
		}
		feed.setItems(itemList);

		List<Feed> feedList = new ArrayList<>();
		feedList.add(feed);
		channel.setFeeds(feedList);

		return channel;
	}

	/**
	 * Process rss feed.
	 */
	@Override
	public void processRssFeed() {
		if (this.rssFeedSources != null && !this.rssFeedSources.isEmpty())
			for (String rssFeed : this.rssFeedSources) {
				try {
					String rssFeedXMLString = getRssFeedXMLString(rssFeed);
					XmlChannelDTO dto = parseXMLString(rssFeedXMLString);
					Channel channel = convertRssFeedToDomainModel(dto);
					addRssFeed(channel);
				} catch (Exception ex) {
					logger.error("an exception occurred", ex);
				}
			}
	}

	/**
	 * Adds the rss feed.
	 *
	 * @param rssChannel the rss channel
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addRssFeed(Channel rssChannel) {
		logger.info("Pushing to database");
		
		String channelLink = rssChannel.getLink();

		Channel existingChannel = this.channelService.getChannelByLink(channelLink);
		if (existingChannel == null) {
			this.channelService.persistChannel(rssChannel);
		} else {
			Feed rssChannelFeed = rssChannel.getFeeds().get(0);
			rssChannelFeed.setChannel(existingChannel);

			Feed lastFeed = this.feedService.getLastFeedForChannel(channelLink);
			if (lastFeed != null) {
				if (rssChannelFeed.getLastBuildDate().after(lastFeed.getLastBuildDate())) {
					List<Item> feedItemsList = rssChannelFeed.getItems();
					List<Item> updatedFeedItemList = new ArrayList<>();
					rssChannelFeed.setItems(null);
					rssChannelFeed = this.feedService.addFeed(rssChannelFeed);
					for (Item feedItem : feedItemsList) {
						Item existingItem = this.itemService.getPreviousItem(feedItem.getGuid());
						if (existingItem != null) {
							if (feedItem.getPubDate().after(existingItem.getPubDate())) {
								existingItem.setFeed(rssChannelFeed);
								existingItem.setDescription(feedItem.getDescription());
								existingItem.setPubDate(feedItem.getPubDate());
								existingItem.setTitle(feedItem.getTitle());
								updatedFeedItemList.add(existingItem);
							}
						} else {
							updatedFeedItemList.add(feedItem);
						}
					}
					rssChannelFeed.setItems(updatedFeedItemList);
					this.feedService.addFeed(rssChannelFeed);
				}
			} else {
				this.feedService.addFeed(rssChannelFeed);
			}
		}
	}
}
