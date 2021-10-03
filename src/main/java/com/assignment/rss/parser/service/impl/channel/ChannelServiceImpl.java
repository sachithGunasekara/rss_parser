package com.assignment.rss.parser.service.impl.channel;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.rss.parser.model.Channel;
import com.assignment.rss.parser.repository.ChannelRepository;
import com.assignment.rss.parser.service.channel.ChannelService;

/**
 * The Class ChannelServiceImpl.
 *
 * @author sachi
 */
@Service
public class ChannelServiceImpl implements ChannelService {

	/** The channel repository. */
	@Autowired
	private ChannelRepository channelRepository;

	/**
	 * Persist channel.
	 *
	 * @param channel the channel
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Channel persistChannel(Channel channel) {
		return this.channelRepository.save(channel);
	}

	/**
	 * Gets the channel by link.
	 *
	 * @param channelLink the channel link
	 * @return the channel by link
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Channel getChannelByLink(String channelLink) {
		Optional<Channel> optionalChannel = this.channelRepository.findByLink(channelLink);
		return optionalChannel.isPresent() ? optionalChannel.get() : null;
	}

}
