package com.assignment.rss.parser.service.rssfeed;

import java.text.ParseException;

import com.assignment.rss.parser.dto.rssfeed.XmlChannelDTO;
import com.assignment.rss.parser.model.Channel;

/**
 * The Interface RssFeedService.
 *
 * @author sachi
 */
public interface RssFeedService {

	/**
	 * Parses the XML string.
	 *
	 * @param rssFeedXml the rss feed xml
	 * @return the xml channel DTO
	 */
	XmlChannelDTO parseXMLString(String rssFeedXml);

	/**
	 * Gets the rss feed XML string.
	 *
	 * @param rssFeedURL the rss feed URL
	 * @return the rss feed XML string
	 */
	String getRssFeedXMLString(String rssFeedURL);

	/**
	 * Convert rss feed to domain model.
	 *
	 * @param dto the dto
	 * @return the channel
	 * @throws ParseException the parse exception
	 */
	Channel convertRssFeedToDomainModel(XmlChannelDTO dto) throws ParseException;

	/**
	 * Process rss feed.
	 */
	void processRssFeed();

	/**
	 * Adds the rss feed.
	 *
	 * @param rssChannel the rss channel
	 */
	void addRssFeed(Channel rssChannel);
}
