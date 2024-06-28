package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.MultipleChoiceQuestion;
import com.example.response.SuccessResponse;
import com.example.service.MultipleChoiceQuestionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/questions")
public class MultipleChoiceQuestionController {

    @Autowired
    MultipleChoiceQuestionService multipleChoiceQuestionService;
    
    @PostMapping
    public ResponseEntity<SuccessResponse> saveQuestion(@RequestBody MultipleChoiceQuestion question){
        MultipleChoiceQuestion questionSaved = multipleChoiceQuestionService.saveQuestion(question);
        SuccessResponse successResponse = new SuccessResponse("Question created Successfully", HttpStatus.CREATED.value(), questionSaved); 
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<SuccessResponse> getAllQuestionsData(){
        List<MultipleChoiceQuestion> allQuestions = multipleChoiceQuestionService.getAllQuestionsData();
        SuccessResponse successResponse = new SuccessResponse("All Questions data retrieved successfully.", HttpStatus.OK.value(), allQuestions);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getQuestionById(@RequestParam("questionId") Integer questionId){
        MultipleChoiceQuestion question = multipleChoiceQuestionService.getQuestionData(questionId);
        SuccessResponse successResponse = new SuccessResponse("Question found and retrieved successfully.", HttpStatus.FOUND.value(), question);
        return new ResponseEntity<>(successResponse, HttpStatus.FOUND);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<SuccessResponse> updateQuestion(@PathVariable("questionId") Integer questionId, @RequestBody MultipleChoiceQuestion question){
        MultipleChoiceQuestion updatedQuestion = multipleChoiceQuestionService.updateQuestion(questionId, question);
        SuccessResponse successResponse = new SuccessResponse("Question data updated successfully.", HttpStatus.OK.value(), updatedQuestion);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<SuccessResponse> deleteQuestionById(@PathVariable("questionId") Integer questionId){
        multipleChoiceQuestionService.deleteQuestion(questionId);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Question deleted successfully");
            successResponse.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
