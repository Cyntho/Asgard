package org.cyntho.asgard.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.cyntho.asgard.dto.NewsDto;
import org.cyntho.asgard.entity.NewsEntity;
import org.cyntho.asgard.repository.NewsRepository;
import org.cyntho.asgard.service.INewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements INewsService {

	private final NewsRepository newsRepository;

	@Override
	public List<NewsDto> getNews() {
		return newsRepository.findAll()
				.stream().map(this::transformToDto).collect(Collectors.toList());
	}

	@Override
	public NewsDto getNewsById(long id) {
		try {
			NewsEntity entity = newsRepository.getReferenceById(id);
			return transformToDto(entity);
		} catch (EntityNotFoundException e){
			return null;
		}
	}

	private NewsDto transformToDto(NewsEntity entity) {
		NewsDto dto = new NewsDto();
		BeanUtils.copyProperties(entity, dto);
		dto.newsId = entity.getNewsId();
		return dto;
	}
}
