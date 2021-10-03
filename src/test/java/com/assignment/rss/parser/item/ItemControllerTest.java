package com.assignment.rss.parser.item;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.rss.parser.model.Item;
import com.assignment.rss.parser.model.ItemResponseDTO;
import com.assignment.rss.parser.repository.ItemRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@TestMethodOrder(OrderAnnotation.class)
public class ItemControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ItemRepository itemRepository;

	@Test
	@Order(1)
	void testNewestItems() throws InterruptedException {
		Thread.sleep(5000L);
		ResponseEntity<ItemResponseDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/items", ItemResponseDTO.class);

		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(10, responseEntity.getBody().getItems().size());
	}

	@Test
	@Order(2)
	void testInvalidPageNumber() throws InterruptedException {
		ResponseEntity<ItemResponseDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/items?page=0", ItemResponseDTO.class);

		assertEquals(400, responseEntity.getStatusCodeValue());
	}

	@Test
	@Order(3)
	void testInvalidPageSize() throws InterruptedException {
		ResponseEntity<ItemResponseDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/items?size=0", ItemResponseDTO.class);

		assertEquals(400, responseEntity.getStatusCodeValue());
	}

	@Test
	@Order(4)
	void testInvalidSortFeild() throws InterruptedException {
		ResponseEntity<ItemResponseDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/items?sort=name", ItemResponseDTO.class);

		assertEquals(400, responseEntity.getStatusCodeValue());
	}

	@Test
	@Order(5)
	void testInvalidSortDirection() throws InterruptedException {
		ResponseEntity<ItemResponseDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/items?direction=up", ItemResponseDTO.class);

		assertEquals(400, responseEntity.getStatusCodeValue());
	}

	@Test
	@Order(6)
	void testPageNumberAndSize() throws InterruptedException {
		ResponseEntity<ItemResponseDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/items?page=1&size=5", ItemResponseDTO.class);

		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(5, responseEntity.getBody().getItems().size());
		assertEquals(1, responseEntity.getBody().getCurrentPage());
	}

	@Test
	@Order(7)
	void testSortDescending() throws InterruptedException {
		ResponseEntity<ItemResponseDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/items?direction=desc", ItemResponseDTO.class);

		Optional<Item> optionalTopItem = itemRepository.findFirstByOrderByPubDateDesc();
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(10, responseEntity.getBody().getItems().size());

		assertEquals(optionalTopItem.get().getGuid(), responseEntity.getBody().getItems().get(0).getLink());
	}

	@Test
	@Order(8)
	void testSortAscending() throws InterruptedException {
		ResponseEntity<ItemResponseDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/items?direction=asc", ItemResponseDTO.class);

		Optional<Item> optionalTopItem = itemRepository.findFirstByOrderByPubDateAsc();
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(10, responseEntity.getBody().getItems().size());

		assertEquals(optionalTopItem.get().getGuid(), responseEntity.getBody().getItems().get(0).getLink());
	}
}
