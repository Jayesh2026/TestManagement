package com.example.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.model.MultipleChoiceQuestion;

public interface MultipleChoiceQuestionService {

    // save new question
    public MultipleChoiceQuestion saveQuestion(MultipleChoiceQuestion question);
    
    // get all questions
    public List<MultipleChoiceQuestion> getAllQuestionsData();
    
    // get question data by Id
    public MultipleChoiceQuestion getQuestionData(Integer questionId);

    // update question
    public MultipleChoiceQuestion updateQuestion(Integer questionId, MultipleChoiceQuestion question);

    // delete question by ID
    public void deleteQuestion(Integer questionId);

    public void uploadBulkQuestionsFromExcelFile(MultipartFile multipartFile);
}