package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.app.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
	List<Tutorial> findByTitleContaining(String title);
}
