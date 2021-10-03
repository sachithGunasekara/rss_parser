package com.assignment.rss.parser.channel;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.assignment.rss.parser.model.Channel;
import com.assignment.rss.parser.model.Feed;
import com.assignment.rss.parser.model.Item;
import com.assignment.rss.parser.repository.ChannelRepository;

@DataJpaTest
public class ChannelRepositoryTest {

	@Autowired
	private ChannelRepository channelRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void testChannelId() {
		Channel channel = new Channel();
		channel.setLink("link_1");
		channel.setTitle("Test title");
		channel.setDescription("Test description");
		
		Channel savedChannel = testEntityManager.persistAndFlush(channel);
		then(savedChannel).isNotNull();
		then(savedChannel.getId()).isGreaterThan(0);
	}
	
	@Test
	void testChannelCreationDateTime() {
		Channel channel = new Channel();
		channel.setLink("link_1");
		channel.setTitle("Test title");
		channel.setDescription("Test description");
		
		Channel savedChannel = testEntityManager.persistAndFlush(channel);
		then(savedChannel).isNotNull();
		then(savedChannel.getCreateDateTime()).isNotNull();
	}
	
	@Test
	void testFindByLink() {
		Channel channel = new Channel();
		channel.setLink("link_1");
		channel.setTitle("Test title");
		channel.setDescription("Test description");

		Channel savedChannel = testEntityManager.persistAndFlush(channel);
		Optional<Channel> optionalChannel = channelRepository.findByLink(channel.getLink());

		then(optionalChannel.get()).isNotNull();
		then(optionalChannel.get().getLink()).isEqualTo(savedChannel.getLink());
	}

	@Test
	void testFeedPersistOnChannelPersist() {
		Channel channel = new Channel();
		channel.setLink("link_1");
		channel.setTitle("Test title");
		channel.setDescription("Test description");

		Feed feed = new Feed();
		feed.setChannel(channel);
		feed.setLastBuildDate(new Date());

		List<Feed> feedList = new ArrayList<>();
		feedList.add(feed);
		channel.setFeeds(feedList);

		Channel savedChannel = testEntityManager.persistAndFlush(channel);
		
		then(savedChannel).isNotNull();
		then(savedChannel.getFeeds().size()).isEqualTo(1);
	}

	@Test
	void testItemFeedPersistOnChannelPersist() {
		Channel channel = new Channel();
		channel.setLink("link_1");
		channel.setTitle("Test title");
		channel.setDescription("Test description");

		Feed feed = new Feed();
		feed.setChannel(channel);
		feed.setLastBuildDate(new Date());

		List<Feed> feedList = new ArrayList<>();
		feedList.add(feed);
		channel.setFeeds(feedList);

		Item item1 = new Item();
		item1.setDescription("Test item1 description");
		item1.setGuid("guid1");
		item1.setTitle("Test item1 title");
		item1.setPubDate(new Date());
		item1.setFeed(feed);

		List<Item> itemList = new ArrayList<>();
		itemList.add(item1);
		feed.setItems(itemList);

		Channel savedChannel = testEntityManager.persistAndFlush(channel);
		
		then(savedChannel).isNotNull();
		then(savedChannel.getFeeds().size()).isEqualTo(1);
		then(savedChannel.getFeeds().get(0).getItems().size()).isEqualTo(1);
	}
}
