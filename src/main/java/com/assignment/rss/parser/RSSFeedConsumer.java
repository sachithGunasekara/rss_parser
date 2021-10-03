package com.assignment.rss.parser;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.assignment.rss.parser.service.rssfeed.RssFeedService;

/**
 * The Class RSSFeedConsumer.
 *
 * @author sachi
 */
@Component
public class RSSFeedConsumer {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(RSSFeedConsumer.class);

	/** The rss feed service. */
	@Autowired
	private RssFeedService rssFeedService;

	/**
	 * Fetch RSS feed.
	 */
	@Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
	public void fetchRSSFeed() {
		logger.info("Fetching rss feed");
		rssFeedService.processRssFeed();
	}
}
