package org.cyntho.asgard.repository;

import org.cyntho.asgard.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
}
