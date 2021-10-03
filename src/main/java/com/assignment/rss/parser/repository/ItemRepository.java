package com.assignment.rss.parser.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.rss.parser.model.Item;

// TODO: Auto-generated Javadoc
/**
 * The Interface ItemRepository.
 *
 * @author sachi
 */
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {

	/**
	 * Find by guid and pub date before.
	 *
	 * @param guid the guid
	 * @param date the date
	 * @return the optional
	 */
	Optional<Item> findByGuidAndPubDateBefore(String guid, Date date);

	/**
	 * Find by guid.
	 *
	 * @param guid the guid
	 * @return the optional
	 */
	Optional<Item> findByGuid(String guid);

	/**
	 * Find first 10 by order by pub date desc.
	 *
	 * @return the optional
	 */
	Optional<List<Item>> findFirst10ByOrderByPubDateDesc();

	/**
	 * Find first by order by pub date desc.
	 *
	 * @return the optional
	 */
	Optional<Item> findFirstByOrderByPubDateDesc();

	/**
	 * Find first by order by pub date asc.
	 *
	 * @return the optional
	 */
	Optional<Item> findFirstByOrderByPubDateAsc();
}
