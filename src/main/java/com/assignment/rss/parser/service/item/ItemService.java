package com.assignment.rss.parser.service.item;

import java.util.Map;

import com.assignment.rss.parser.model.Item;

/**
 * The Interface ItemService.
 *
 * @author sachi
 */
public interface ItemService {

	/**
	 * Gets the previous item.
	 *
	 * @param guid the guid
	 * @return the previous item
	 */
	Item getPreviousItem(String guid);

	/**
	 * Update item.
	 *
	 * @param item the item
	 */
	void updateItem(Item item);

	/**
	 * Gets the paginated and sorted items.
	 *
	 * @param page      the page
	 * @param size      the size
	 * @param sortField the sort field
	 * @param order     the order
	 * @return the paginated and sorted items
	 */
	Map<String, Object> getPaginatedAndSortedItems(int page, int size, String sortField, String order);
}
