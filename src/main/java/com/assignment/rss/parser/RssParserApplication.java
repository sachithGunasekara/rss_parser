package com.assignment.rss.parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * The Class RssParserApplication.
 *
 * @author sachi
 */
@SpringBootApplication(scanBasePackages = "com.assignment.rss.parser")
@EnableJpaRepositories(basePackages = "com.assignment.rss.parser.repository")
@EntityScan(basePackages = { "com.assignment.rss.parser.model" })
@EnableScheduling
@EnableTransactionManagement
public class RssParserApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(RssParserApplication.class, args);
	}
}
