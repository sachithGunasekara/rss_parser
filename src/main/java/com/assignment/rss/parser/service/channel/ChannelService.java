package com.assignment.rss.parser.service.channel;

import com.assignment.rss.parser.model.Channel;

/**
 * The Interface ChannelService.
 *
 * @author sachi
 */
public interface ChannelService {

	/**
	 * Persist channel.
	 *
	 * @param channel the channel
	 * @return the channel
	 */
	Channel persistChannel(Channel channel);

	/**
	 * Gets the channel by link.
	 *
	 * @param channelLink the channel link
	 * @return the channel by link
	 */
	Channel getChannelByLink(String channelLink);
}
