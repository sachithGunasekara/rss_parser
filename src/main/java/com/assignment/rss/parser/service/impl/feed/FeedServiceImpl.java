package com.assignment.rss.parser.service.impl.feed;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.rss.parser.model.Feed;
import com.assignment.rss.parser.repository.FeedRepository;
import com.assignment.rss.parser.service.feed.FeedService;

/**
 * The Class FeedServiceImpl.
 *
 * @author sachi
 */
@Service
public class FeedServiceImpl implements FeedService {

	/** The feed repository. */
	@Autowired
	private FeedRepository feedRepository;

	/**
	 * Gets the last feed for channel.
	 *
	 * @param channelLink the channel link
	 * @return the last feed for channel
	 */
	@Override
	public Feed getLastFeedForChannel(String channelLink) {
		Optional<Feed> optionalFeed = this.feedRepository.findFirstByChannel_linkOrderByLastBuildDateDesc(channelLink);
		return optionalFeed.isPresent() ? optionalFeed.get() : null;
	}

	/**
	 * Adds the feed.
	 *
	 * @param feed the feed
	 * @return the feed
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Feed addFeed(Feed feed) {
		return this.feedRepository.save(feed);
	}

}
