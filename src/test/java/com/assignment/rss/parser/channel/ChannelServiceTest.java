package com.assignment.rss.parser.channel;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.rss.parser.model.Channel;
import com.assignment.rss.parser.repository.ChannelRepository;
import com.assignment.rss.parser.service.channel.ChannelService;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
public class ChannelServiceTest {

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelRepository channelRepository;
	
	@Test
	void testChannelServicePersist() {
		Channel channel = new Channel();
		channel.setLink("link_1");
		channel.setTitle("Test title");
		channel.setDescription("Test description");

		Channel savedChannel = channelService.persistChannel(channel);
		Optional<Channel> optionalChannel = this.channelRepository.findByLink(savedChannel.getLink());
		
		then(savedChannel).isNotNull();
		then(optionalChannel.get()).isNotNull();
		then(optionalChannel.get().getLink()).isEqualTo(savedChannel.getLink());
	}
	
	@Test
	void testgetChannelByLink() {
		Channel channel = new Channel();
		channel.setLink("link_1");
		channel.setTitle("Test title");
		channel.setDescription("Test description");
		
		Channel savedChannel = this.channelRepository.save(channel);
		Channel retrievedChannel = channelService.getChannelByLink(savedChannel.getLink());
		
		then(retrievedChannel).isNotNull();
		then(retrievedChannel.getLink()).isEqualTo(savedChannel.getLink());
	}
}
