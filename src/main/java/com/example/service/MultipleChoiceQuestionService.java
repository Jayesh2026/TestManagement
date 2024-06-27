package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.MultipleChoiceQuestion;
import com.example.repository.MultipleChoiceQuestionRepository;

@Service
public class MultipleChoiceQuestionService {

    @Autowired
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    
    public MultipleChoiceQuestion savQuestion(MultipleChoiceQuestion question){
        return multipleChoiceQuestionRepository.save(question);
    }

    public List<MultipleChoiceQuestion> getAllQuestionsData(){
        return multipleChoiceQuestionRepository.findAll();
    }

}
