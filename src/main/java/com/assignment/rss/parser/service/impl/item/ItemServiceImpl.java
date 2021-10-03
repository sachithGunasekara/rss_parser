package com.assignment.rss.parser.service.impl.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.rss.parser.dto.item.ItemDTO;
import com.assignment.rss.parser.exception.custom.ItemNotFoundException;
import com.assignment.rss.parser.model.Item;
import com.assignment.rss.parser.repository.ItemRepository;
import com.assignment.rss.parser.service.item.ItemService;

/**
 * The Class ItemServiceImpl.
 *
 * @author sachi
 */
@Service
public class ItemServiceImpl implements ItemService {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	/** The item repository. */
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * Gets the previous item.
	 *
	 * @param guid the guid
	 * @return the previous item
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Item getPreviousItem(String guid) {
		Optional<Item> optionalItem = this.itemRepository.findByGuid(guid);
		return optionalItem.isPresent() ? optionalItem.get() : null;
	}

	/**
	 * Update item.
	 *
	 * @param item the item
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateItem(Item item) {
		this.itemRepository.saveAndFlush(item);
	}

	/**
	 * Gets the paginated and sorted items.
	 *
	 * @param page      the page
	 * @param size      the size
	 * @param sortField the sort field
	 * @param order     the order
	 * @return the paginated and sorted items
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Map<String, Object> getPaginatedAndSortedItems(int page, int size, String sortField, String order) {
		logger.info("fetching items");

		Map<String, Object> responseMap = new HashMap<>();
		Pageable pageable = PageRequest.of(page, size, Sort.by(order.equals("desc") ? Sort.Direction.DESC
				: order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField));
		Page<Item> findByField = itemRepository.findAll(pageable);
		List<Item> itemList = findByField.getContent();
		if (itemList == null || itemList.isEmpty()) {
			throw new ItemNotFoundException();
		}
		List<ItemDTO> itemDTOList = new ArrayList<>(itemList.size());

		for (Item item : itemList) {
			ItemDTO dto = new ItemDTO();
			dto.setLink(item.getGuid());
			dto.setTitle(item.getTitle());
			dto.setDescription(item.getDescription());
			dto.setPublishDate(item.getPubDate().toString());
			dto.setCreatedDate(item.getCreateDateTime().toString());
			dto.setLastModifiedDate(item.getUpdateDateTime().toString());
			itemDTOList.add(dto);
		}

		responseMap.put("currentPage", findByField.getNumber() + 1);
		responseMap.put("totalItems", findByField.getTotalElements());
		responseMap.put("totalPages", findByField.getTotalPages());
		responseMap.put("hasNext", findByField.hasNext());
		responseMap.put("hasPrevious", findByField.hasPrevious());
		responseMap.put("items", itemDTOList);

		return responseMap;
	}

}
