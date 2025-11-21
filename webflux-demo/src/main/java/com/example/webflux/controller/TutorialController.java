package com.example.webflux.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.webflux.model.Tutorial;
import com.example.webflux.service.TutorialService;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

    private final TutorialService service;

    public TutorialController(TutorialService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> create(@RequestBody Tutorial tutorial) {
        return service.create(tutorial)
            .map(saved -> {
                Map<String, Object> response = new HashMap<>();
                response.put("id", saved.getId());

                return ResponseEntity
                        .created(null)
                        .body(response);
            });
    }

    @GetMapping
    public Flux<Tutorial> getAll(@RequestParam(required = false) String title) {
        return title != null ? service.findByTitle(title) : service.getAll();
    }

    @GetMapping("/published")
    public Flux<Tutorial> getPublished() {
        return service.findByPublished(true);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Tutorial>> getById(@PathVariable Long id) {
        return service.getById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Tutorial>> update(
            @PathVariable Long id,
            @RequestBody Tutorial tutorial) {

        return service.update(id, tutorial)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.deleteById(id)
            .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteAll() {
        return service.deleteAll()
            .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
