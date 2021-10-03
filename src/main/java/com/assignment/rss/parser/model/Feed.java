package com.assignment.rss.parser.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * The Class Feed.
 *
 * @author sachi
 */
@Entity
@Table(name = "feed", schema = "rss_feed")
public class Feed implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1675509763830561583L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** The create date time. */
	@CreationTimestamp
	@Column(name = "created_on")
	private LocalDateTime createDateTime;

	/** The update date time. */
	@UpdateTimestamp
	@Column(name = "updated_on")
	private LocalDateTime updateDateTime;

	/** The last build date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_build_date", nullable = false)
	private Date lastBuildDate;

	/** The channel. */
	@ManyToOne(targetEntity = Channel.class, optional = false)
	private Channel channel;

	/** The items. */
	@OneToMany(mappedBy = "feed", fetch = FetchType.LAZY, targetEntity = Item.class, cascade = CascadeType.ALL)
	private List<Item> items;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the last build date.
	 *
	 * @return the last build date
	 */
	public Date getLastBuildDate() {
		return lastBuildDate;
	}

	/**
	 * Sets the last build date.
	 *
	 * @param lastBuildDate the new last build date
	 */
	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	/**
	 * Gets the channel.
	 *
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Sets the channel.
	 *
	 * @param channel the new channel
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * Gets the creates the date time.
	 *
	 * @return the creates the date time
	 */
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	/**
	 * Sets the creates the date time.
	 *
	 * @param createDateTime the new creates the date time
	 */
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	/**
	 * Gets the update date time.
	 *
	 * @return the update date time
	 */
	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	/**
	 * Sets the update date time.
	 *
	 * @param updateDateTime the new update date time
	 */
	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

}
