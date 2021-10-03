package com.assignment.rss.parser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.rss.parser.model.Feed;

/**
 * The Interface FeedRepository.
 *
 * @author sachi
 */
@Transactional
public interface FeedRepository extends JpaRepository<Feed, Long> {

	/**
	 * Find first by channel link order by last build date desc.
	 *
	 * @param channelLink the channel link
	 * @return the optional
	 */
	Optional<Feed> findFirstByChannel_linkOrderByLastBuildDateDesc(String channelLink);
}
