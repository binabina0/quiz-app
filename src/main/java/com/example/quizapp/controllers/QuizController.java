package com.example.quizapp.controllers;

import com.example.quizapp.enteties.QuestionWrapper;
import com.example.quizapp.enteties.Response;
import com.example.quizapp.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;
    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }
    @GetMapping("get/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}
