package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.model.MultipleChoiceQuestion;
import com.example.response.SuccessResponse;
import com.example.service.MultipleChoiceQuestionService;

@ExtendWith(MockitoExtension.class)
public class MultipleChoiceQuestionControllerTest {
    
    @InjectMocks
    private MultipleChoiceQuestionController multipleChoiceQuestionController;

    @Mock
    private MultipleChoiceQuestionService multipleChoiceQuestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveQuestion() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setCategory("Java");
        question.setQuestion("Question is a Question ?");
        question.setOptionOne("Option 1");
        question.setOptionTwo("Option 2");
        question.setOptionThree("Option 3");
        question.setOptionFour("Option 4");
        question.setCorrectOption("1");
        question.setPositiveMark("3");
        question.setNegativeMark("-1");

        when(multipleChoiceQuestionService.saveQuestion(question)).thenReturn(question);
        SuccessResponse successResponse = new SuccessResponse("Question created Successfully", HttpStatus.CREATED.value(), question);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse,HttpStatus.CREATED);
        ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.saveQuestion(question);
        assertEquals(expectedResult,response);
    }

    @Test
    void testGetAllQuestionsData() {
        MultipleChoiceQuestion question1 = new MultipleChoiceQuestion();
        question1.setQuestionId(1);
        question1.setCategory("SpringBoot");
        question1.setOptionOne("@Controller and @PostMapping");
        question1.setOptionTwo("@Controller and @Component");
        question1.setOptionThree("@Controller and @ResponseBody");
        question1.setOptionFour("@Controller and @ResponseStatus");
        question1.setCorrectOption("@Controller and @ResponseBody");
        question1.setPositiveMark("3");
        question1.setNegativeMark("-1");

        MultipleChoiceQuestion question2 = new MultipleChoiceQuestion();
        question2.setCategory("Java");
        question2.setQuestion("Question is a Question");
        question2.setOptionOne("Option_1");
        question2.setOptionTwo("Option_2");
        question2.setOptionThree("Option_3");
        question2.setOptionFour("Option_4");
        question2.setCorrectOption("2");
        question2.setPositiveMark("4");
        question2.setNegativeMark("-1");

        List<MultipleChoiceQuestion> mockQuestions = Arrays.asList(question1, question2);

        when(multipleChoiceQuestionService.getAllQuestionsData()).thenReturn(mockQuestions);

        SuccessResponse successResponse = new SuccessResponse("All Questions data retrieved successfully.", HttpStatus.OK.value(), mockQuestions);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse,HttpStatus.OK);
        ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.getAllQuestionsData();
        assertEquals(expectedResult,response);
    }

    @Test
    void testGetQuestionById() {
        int questionId = 1;
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setQuestionId(questionId);
        question.setCategory("Java");
        question.setQuestion("Question is a Question");
        question.setOptionOne("Option_1");
        question.setOptionTwo("Option_2");
        question.setOptionThree("Option_3");
        question.setOptionFour("Option_4");
        question.setCorrectOption("2");
        question.setPositiveMark("4");
        question.setNegativeMark("-1");

        when(multipleChoiceQuestionService.getQuestionData(questionId)).thenReturn(question);

        SuccessResponse successResponse = new SuccessResponse("Question found and retrieved successfully.", HttpStatus.FOUND.value(), question);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.FOUND);

        ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.getQuestionById(questionId);

        assertEquals(expectedResult.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResult.getBody(), response.getBody());

        verify(multipleChoiceQuestionService, times(1)).getQuestionData(questionId);
    }

    @Test
    void testUpdateQuestion() {
        int questionId = 1;
        MultipleChoiceQuestion updatedQuestion = new MultipleChoiceQuestion();
        updatedQuestion.setQuestionId(questionId);
        updatedQuestion.setCategory("Java");
        updatedQuestion.setQuestion("Question is a Question");
        updatedQuestion.setOptionOne("Option_1");
        updatedQuestion.setOptionTwo("Option_2");
        updatedQuestion.setOptionThree("Option_3");
        updatedQuestion.setOptionFour("Option_4");
        updatedQuestion.setCorrectOption("2");
        updatedQuestion.setPositiveMark("4");
        updatedQuestion.setNegativeMark("-1");

        when(multipleChoiceQuestionService.updateQuestion(questionId, updatedQuestion)).thenReturn(updatedQuestion);

        SuccessResponse successResponse = new SuccessResponse("Question data updated successfully.", HttpStatus.OK.value(), updatedQuestion);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.OK);

        ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.updateQuestion(questionId, updatedQuestion);

        assertEquals(expectedResult.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResult.getBody(), response.getBody());

        verify(multipleChoiceQuestionService, times(1)).updateQuestion(questionId, updatedQuestion);
    }

    @Test
    void testDeleteQuestionById() {
        int questionId = 1;
        doNothing().when(multipleChoiceQuestionService).deleteQuestion(questionId);

        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Question deleted successfully");
        successResponse.setStatusCode(HttpStatus.OK.value());
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.OK);

        ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.deleteQuestionById(questionId);

        assertEquals(expectedResult.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResult.getBody(), response.getBody());

        verify(multipleChoiceQuestionService, times(1)).deleteQuestion(questionId);
    }
}
