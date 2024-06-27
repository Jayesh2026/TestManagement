package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.MultipleChoiceQuestion;
import com.example.service.MultipleChoiceQuestionService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/questions")
public class MultipleChoiceQuestionController {

    @Autowired
    MultipleChoiceQuestionService multipleChoiceQuestionService;
    
    @PostMapping
    public ResponseEntity<MultipleChoiceQuestion> savQuestion(@RequestBody MultipleChoiceQuestion question){
        return new ResponseEntity<>(multipleChoiceQuestionService.savQuestion(question), HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<MultipleChoiceQuestion>> getAllQuestionsData(){
        List<MultipleChoiceQuestion> allQuestions = multipleChoiceQuestionService.getAllQuestionsData();
        return new ResponseEntity<>(allQuestions, HttpStatus.OK);
    }

}
