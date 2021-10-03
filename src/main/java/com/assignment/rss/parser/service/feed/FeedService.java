package com.assignment.rss.parser.service.feed;

import com.assignment.rss.parser.model.Feed;

/**
 * The Interface FeedService.
 *
 * @author sachi
 */
public interface FeedService {

	/**
	 * Gets the last feed for channel.
	 *
	 * @param channelLink the channel link
	 * @return the last feed for channel
	 */
	Feed getLastFeedForChannel(String channelLink);

	/**
	 * Adds the feed.
	 *
	 * @param feed the feed
	 * @return the feed
	 */
	Feed addFeed(Feed feed);
}
