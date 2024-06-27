package com.example.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.ObjectIsNullException;
import com.example.exception.QuestionNotFoundException;
import com.example.model.MultipleChoiceQuestion;
import com.example.repository.MultipleChoiceQuestionRepository;

@Service
public class MultipleChoiceQuestionServiceImpl implements MultipleChoiceQuestionService {

    public static final Logger logger = LoggerFactory.getLogger(MultipleChoiceQuestionServiceImpl.class);

    @Autowired
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    public MultipleChoiceQuestion savQuestion(MultipleChoiceQuestion question) {
        if (question == null) {
            throw new ObjectIsNullException("Question cannot be null");
        }
        try {
            return multipleChoiceQuestionRepository.save(question);
        } catch (Exception exception) {
            logger.error("Error while saving question: {}", exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    public List<MultipleChoiceQuestion> getAllQuestionsData() {
        List<MultipleChoiceQuestion> questions = multipleChoiceQuestionRepository.findAll();
        if (questions == null || questions.isEmpty()) {
            throw new QuestionNotFoundException("Unable to retrieved questions, Not found any question.");
        }
        return questions;
    }

    public MultipleChoiceQuestion getQuestionData(Integer questionId) {
        Optional<MultipleChoiceQuestion> questionData = multipleChoiceQuestionRepository.findById(questionId);

        if(!questionData.isPresent()){
            throw new QuestionNotFoundException("This question is not present.");
        }
        return questionData.get();
    }

    @Override
    public MultipleChoiceQuestion updateQuestion(Integer questionId, MultipleChoiceQuestion question) {
        Optional<MultipleChoiceQuestion> existingQuestion = multipleChoiceQuestionRepository.findById(questionId);
        if (!existingQuestion.isPresent()) {
            throw new QuestionNotFoundException("This Question is not found with ID: " + questionId);
        }
        try {
            MultipleChoiceQuestion updateQuestionData = existingQuestion.get();

            if (question.getCategory() != null) {
                updateQuestionData.setCategory(question.getCategory());
            }
            if (question.getQuestion() != null) {
                updateQuestionData.setQuestion(question.getQuestion());
            }
            if (question.getOptionOne() != null) {
                updateQuestionData.setOptionOne(question.getOptionOne());
            }
            if (question.getOptionTwo() != null) {
                updateQuestionData.setOptionTwo(question.getOptionTwo());
            }
            if (question.getOptionThree() != null) {
                updateQuestionData.setOptionThree(question.getOptionThree());
            }
            if (question.getOptionFour() != null) {
                updateQuestionData.setOptionFour(question.getOptionFour());
            }
            if (question.getCorrectOption() != null) {
                updateQuestionData.setCorrectOption(question.getCorrectOption());
            }
            if (question.getPositiveMark() != null) {
                updateQuestionData.setPositiveMark(question.getPositiveMark());
            }
            if (question.getNegativeMark() != null) {
                updateQuestionData.setNegativeMark(question.getNegativeMark());
            }
            return multipleChoiceQuestionRepository.save(updateQuestionData);
        } catch (Exception e) {
            logger.error("Error updating question: {}", e.getMessage());
            throw new RuntimeException("Error updating question", e);
        }
    }

    @Override
    public void deleteQuestion(Integer questionId) {
        Optional<MultipleChoiceQuestion> question = multipleChoiceQuestionRepository.findById(questionId);
    
        if(question.isPresent()){
            multipleChoiceQuestionRepository.deleteById(questionId);
        }else{
            throw new QuestionNotFoundException("This question Id is not found.");
        }
    }
}
