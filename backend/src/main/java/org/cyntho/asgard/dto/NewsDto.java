package org.cyntho.asgard.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class NewsDto {

	public long newsId;
	public String title;
	public String authorName;
	public long authorId;
	public String description;
	public String content;
	public String imageUrl;
	public int popularity;
	public Instant createdAt;
	public Instant updatedAt;
	public String updatedBy;


}
