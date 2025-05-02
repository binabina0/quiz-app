package com.example.quizapp.controllers;

import com.example.quizapp.enteties.Question;
import com.example.quizapp.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.quizapp.services.QuestionService;

import java.sql.Struct;
import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    private QuizService quizService;

    @GetMapping("allQuestions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }
    @PutMapping("update")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<String> deleteQuestion(@PathVariable int id) {
        return questionService.deleteQuestion(id);
    }
}
