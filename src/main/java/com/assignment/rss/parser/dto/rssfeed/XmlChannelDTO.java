package com.assignment.rss.parser.dto.rssfeed;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class XmlChannelDTO.
 *
 * @author sachi
 */
@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class XmlChannelDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3601528024477210996L;

	/** The title. */
	private String title;

	/** The description. */
	private String description;

	/** The link. */
	private String link;

	/** The last build date. */
	private String lastBuildDate;

	/** The item. */
	private List<XmlChannelItemDTO> item;

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the link.
	 *
	 * @param link the new link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public List<XmlChannelItemDTO> getItem() {
		return item;
	}

	/**
	 * Sets the item.
	 *
	 * @param item the new item
	 */
	public void setItem(List<XmlChannelItemDTO> item) {
		this.item = item;
	}

	/**
	 * Gets the last build date.
	 *
	 * @return the last build date
	 */
	public String getLastBuildDate() {
		return lastBuildDate;
	}

	/**
	 * Sets the last build date.
	 *
	 * @param lastBuildDate the new last build date
	 */
	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "XmlChannelDTO [title=" + title + ", description=" + description + ", link=" + link + ", lastBuildDate="
				+ lastBuildDate + ", item=" + item + "]";
	}

}
