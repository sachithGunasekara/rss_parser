package com.assignment.rss.parser.controller.item;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rss.parser.exception.custom.InavlidPageNumberException;
import com.assignment.rss.parser.exception.custom.InavlidPageSizeException;
import com.assignment.rss.parser.exception.custom.InvalidSortFieldException;
import com.assignment.rss.parser.exception.custom.InvalidSortingDirection;
import com.assignment.rss.parser.model.Item;
import com.assignment.rss.parser.service.item.ItemService;

/**
 * The Class ItemController.
 *
 * @author sachi
 */
@RestController
public class ItemController {

	private Logger logger = LoggerFactory.getLogger(ItemController.class);

	/** The item service. */
	@Autowired
	private ItemService itemService;

	/**
	 * Gets the paginated and sorted items.
	 *
	 * @param page      the page
	 * @param size      the size
	 * @param sort      the sort
	 * @param direction the direction
	 * @return the paginated and sorted items
	 */
	@GetMapping(path = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getPaginatedAndSortedItems(
			@RequestParam(defaultValue = "1", required = false) int page,
			@RequestParam(defaultValue = "10", required = false) int size,
			@RequestParam(defaultValue = "pubDate", required = false) String sort,
			@RequestParam(defaultValue = "desc", required = false) String direction) {
		Map<String, Object> responseMap = null;
		this.validateRequest(--page, size, sort, direction);
		responseMap = this.itemService.getPaginatedAndSortedItems(page, size, sort, direction);
		return ResponseEntity.ok(responseMap);
	}

	/**
	 * Validate request.
	 *
	 * @param page the page
	 * @param sort the sort
	 */
	private void validateRequest(int page, int size, String sort, String direction) {
		logger.info("validating request");
		Field[] allFields = Item.class.getDeclaredFields();
		List<String> allowedDirections = Arrays.asList(new String[] { "asc", "desc", });

		if (page < 0) {
			throw new InavlidPageNumberException();
		}

		if (size < 1) {
			throw new InavlidPageSizeException();
		}

		if (!Arrays.stream(allFields).anyMatch(field -> field.getName().equals(sort))) {
			throw new InvalidSortFieldException();
		}

		if (allowedDirections.indexOf(direction) == -1) {
			throw new InvalidSortingDirection();
		}

	}
}
