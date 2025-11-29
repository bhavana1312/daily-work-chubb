package com.example.demo.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.QuestionWrapper;
import com.example.demo.entity.Quiz;
import com.example.demo.entity.Response;
import com.example.demo.feign.QuizInterface;
import com.example.demo.repository.QuizRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizDao;

    @Autowired
    QuizInterface quizInterface;

    private static final String CB_NAME = "questionServiceCB";


    @CircuitBreaker(name = CB_NAME, fallbackMethod = "createQuizFallback")
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> createQuizFallback(String category, int numQ, String title, Throwable ex) {
        System.out.println("Fallback createQuiz: " + ex.getMessage());
        return new ResponseEntity<>("Question Service is Down! Try later.", HttpStatus.SERVICE_UNAVAILABLE);
    }


    @CircuitBreaker(name = CB_NAME, fallbackMethod = "getQuizQuestionsFallback")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestionsFallback(Integer id, Throwable ex) {
        System.out.println("Fallback getQuizQuestions: " + ex.getMessage());
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
    }


    @CircuitBreaker(name = CB_NAME, fallbackMethod = "calculateResultFallback")
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }


    public ResponseEntity<Integer> calculateResultFallback(Integer id, List<Response> responses, Throwable ex) {
        System.out.println("Fallback calculateResult: " + ex.getMessage());
        return new ResponseEntity<>(0, HttpStatus.SERVICE_UNAVAILABLE);
    }
}