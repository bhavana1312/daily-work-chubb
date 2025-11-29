package com.app.quizApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.quizApp.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer>{

}
