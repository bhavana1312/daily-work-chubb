package com.example.webflux.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import com.example.webflux.model.Tutorial;

public interface TutorialRepository extends R2dbcRepository<Tutorial, Long> {
    Flux<Tutorial> findByPublished(Boolean published);
    Flux<Tutorial> findByTitleContaining(String title);
}