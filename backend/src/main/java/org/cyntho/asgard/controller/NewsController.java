package org.cyntho.asgard.controller;


import lombok.RequiredArgsConstructor;
import org.cyntho.asgard.dto.NewsDto;
import org.cyntho.asgard.service.INewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/news")
@RequiredArgsConstructor
public class NewsController {

	private final INewsService newsService;

	@GetMapping
	public List<NewsDto> getNews() throws InterruptedException {
		return newsService.getNews();
	}

	@GetMapping("/{id}")
	public NewsDto getNewsDetails(@PathVariable Long id) {
		return newsService.getNewsById(id);
	}
}
