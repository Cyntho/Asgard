package org.cyntho.asgard.service;

import org.cyntho.asgard.dto.NewsDto;
import java.util.List;


public interface INewsService {

	List<NewsDto> getNews();
	NewsDto getNewsById(long id);
}
