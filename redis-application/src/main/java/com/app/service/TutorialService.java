package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.model.Tutorial;
import com.app.repository.TutorialRepository;

@Service
public class TutorialService {

	@Autowired
	private TutorialRepository repo;

	public List<Tutorial> findAll() {
		return repo.findAll();
	}

	@Cacheable(value = "tutorial", key = "#id")
	public Optional<Tutorial> findById(Long id) {
		return repo.findById(id);
	}

	@Cacheable(value = "tutorialsByTitle", key = "#title")
	public List<Tutorial> findByTitle(String title) {
		return repo.findByTitleContaining(title);
	}

	@CacheEvict(value = { "tutorial", "tutorialsByTitle" }, allEntries = true)
	public Tutorial save(Tutorial t) {
		return repo.save(t);
	}

	@CachePut(value = "tutorial", key = "#id")
	@CacheEvict(value = "tutorialsByTitle", allEntries = true)
	public Tutorial update(Long id, Tutorial t) {
		t.setId(id);
		return repo.save(t);
	}
}
