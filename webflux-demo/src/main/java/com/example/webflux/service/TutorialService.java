package com.example.webflux.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.webflux.model.Tutorial;
import com.example.webflux.repository.TutorialRepository;

@Service
public class TutorialService {

    private final TutorialRepository repo;

    public TutorialService(TutorialRepository repo) {
        this.repo = repo;
    }

    public Mono<Tutorial> create(Tutorial t) { return repo.save(t); }
    public Flux<Tutorial> getAll() { return repo.findAll(); }
    public Mono<Tutorial> getById(Long id) { return repo.findById(id); }
    public Mono<Tutorial> update(Long id, Tutorial t) {
        return repo.findById(id)
            .flatMap(existing -> {
                existing.setTitle(t.getTitle());
                existing.setDescription(t.getDescription());
                existing.setPublished(t.getPublished());
                return repo.save(existing);
            });
    }
    public Mono<Void> deleteById(Long id) { return repo.deleteById(id); }
    public Mono<Void> deleteAll() { return repo.deleteAll(); }
    public Flux<Tutorial> findByPublished(Boolean p) { return repo.findByPublished(p); }
    public Flux<Tutorial> findByTitle(String title) { return repo.findByTitleContaining(title); }
}