package com.app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.service.TutorialService;
import com.app.model.Tutorial;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

	@Autowired
	private TutorialService service;

	@GetMapping
	public List<Tutorial> getAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Tutorial> getById(@PathVariable Long id) {
		return service.findById(id);
	}

	@GetMapping("/search")
	public List<Tutorial> search(@RequestParam String title) {
		return service.findByTitle(title);
	}

	@PostMapping
	public Tutorial create(@RequestBody Tutorial tutorial) {
		return service.save(tutorial);
	}
}
