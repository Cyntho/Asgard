package org.cyntho.asgard.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "NEWS")
public class NewsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NEWS_ID", nullable = false)
	private Long newsId;

	@Column(name = "AUTHOR_ID")
	private long authorId;

	@Column(name = "TITLE", length = 250)
	private String title;

	@Column(name = "DESCRIPTION", length = 1024)
	@Lob
	private String description;

	@Column(name = "CONTENT", columnDefinition = "LONGTEXT")
	@Lob
	private String content;

	@Column(name = "POPULARITY", nullable = false)
	private int popularity;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Column(name = "CREATED_AT", nullable = false)
	private Instant createdAt;

	@Column(name = "UPDATED_AT", nullable = false)
	private Instant updatedAt;

	@Column(name = "UPDATED_BY", nullable = false, length = 20)
	private String updatedBy;

}
