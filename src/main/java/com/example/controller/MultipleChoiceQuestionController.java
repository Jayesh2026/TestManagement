package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.MultipleChoiceQuestion;
import com.example.response.SuccessResponse;
import com.example.service.MultipleChoiceQuestionService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Validated
@RestController
@RequestMapping("/api/questions")
public class MultipleChoiceQuestionController {

    private MultipleChoiceQuestionService multipleChoiceQuestionService;

    public MultipleChoiceQuestionController(MultipleChoiceQuestionService multipleChoiceQuestionService) {
        this.multipleChoiceQuestionService = multipleChoiceQuestionService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> saveQuestion(@Valid @RequestBody MultipleChoiceQuestion question) {
        MultipleChoiceQuestion questionSaved = multipleChoiceQuestionService.saveQuestion(question);
        SuccessResponse successResponse = new SuccessResponse("Question created Successfully",
                HttpStatus.CREATED.value(), questionSaved);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<SuccessResponse> getAllQuestionsData() {
        List<MultipleChoiceQuestion> questionsList = multipleChoiceQuestionService.getAllQuestionsData();
        SuccessResponse successResponse = new SuccessResponse("All Questions data retrieved successfully.",
                HttpStatus.OK.value(), questionsList);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity<SuccessResponse> getQuestionById(@PathVariable("question-id") Integer questionId) {
        MultipleChoiceQuestion question = multipleChoiceQuestionService.getQuestionData(questionId);
        SuccessResponse successResponse = new SuccessResponse("Question found and retrieved successfully.",
                HttpStatus.FOUND.value(), question);
        return new ResponseEntity<>(successResponse, HttpStatus.FOUND);
    }

    @PutMapping("/{question-id}")
    public ResponseEntity<SuccessResponse> updateQuestion(@PathVariable("question-id") Integer questionId,
            @Valid @RequestBody MultipleChoiceQuestion question) {
        MultipleChoiceQuestion updatedQuestion = multipleChoiceQuestionService.updateQuestion(questionId, question);
        SuccessResponse successResponse = new SuccessResponse("Question data updated successfully.",
                HttpStatus.OK.value(), updatedQuestion);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity<SuccessResponse> deleteQuestionById(@PathVariable("question-id") Integer questionId) {
        multipleChoiceQuestionService.deleteQuestion(questionId);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Question deleted successfully");
        successResponse.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/saveBulkQuestions")
    public ResponseEntity<SuccessResponse> uploadBulkQuestions(@RequestParam("file") MultipartFile file) {
        multipleChoiceQuestionService.uploadBulkQuestionsFromExcelFile(file);
        SuccessResponse successResponse = new SuccessResponse("Question Data Uploaded Successfully.", HttpStatus.OK.value(), null);
        return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
    }

}
