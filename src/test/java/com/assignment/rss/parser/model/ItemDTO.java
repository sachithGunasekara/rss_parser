package com.assignment.rss.parser.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -790504789512975227L;

	private String link;
	private String title;
	private String description;
	private String publishDate;
	private String createdDate;
	private String lastModifiedDate;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
