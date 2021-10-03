package com.assignment.rss.parser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.rss.parser.model.Channel;

/**
 * The Interface ChannelRepository.
 *
 * @author sachi
 */
@Transactional
public interface ChannelRepository extends JpaRepository<Channel, Long> {

	/**
	 * Find by link.
	 *
	 * @param link the link
	 * @return the optional
	 */
	Optional<Channel> findByLink(String link);
}
