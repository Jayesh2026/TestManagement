// package com.example.controller;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.ArrayList;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import com.example.model.MultipleChoiceQuestion;
// import com.example.response.SuccessResponse;
// import com.example.service.MultipleChoiceQuestionService;

// @ExtendWith(MockitoExtension.class)
// public class MultipleChoiceQuestionControllerTest {
    
//     @InjectMocks
//     private MultipleChoiceQuestionController multipleChoiceQuestionController;

//     @Mock
//     private MultipleChoiceQuestionService multipleChoiceQuestionService;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testSaveQuestion() {
//         MultipleChoiceQuestion question = new MultipleChoiceQuestion(1, "Java", "Question", "OptionA",
//         "OptionB", "OptionC", "OptionD", "1", "3", "-1");

//         when(multipleChoiceQuestionService.saveQuestion(question)).thenReturn(question);
//         SuccessResponse successResponse = new SuccessResponse("Question created Successfully", HttpStatus.CREATED.value(), question);
//         ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse,HttpStatus.CREATED);
//         ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.saveQuestion(question);
//         assertEquals(expectedResult,response);
//     }

//     @Test
//     void testGetAllQuestionsData() {
//         List<MultipleChoiceQuestion> questionList = new ArrayList<>();
//         questionList.add(new MultipleChoiceQuestion(1, "save 1", "save question", "option A", "option B", "option C",
//                 "option D", "option A", "3", "-1"));
//         questionList.add(new MultipleChoiceQuestion(1, "save 2", "save question1", "option A1", "option B1", "option C1",
//                 "option D1", "option A1", "3", "-1"));

//         when(multipleChoiceQuestionService.getAllQuestionsData()).thenReturn(questionList);

//         SuccessResponse successResponse = new SuccessResponse("All Questions data retrieved successfully.", HttpStatus.OK.value(), questionList);
//         ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse,HttpStatus.OK);
//         ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.getAllQuestionsData();
//         assertEquals(expectedResult,response);
//     }

//     @Test
//     void testGetQuestionById() {
//         int questionId = 1;
//         MultipleChoiceQuestion question = new MultipleChoiceQuestion(1, "Java", "Question", "OptionA",
//         "OptionB", "OptionC", "OptionD", "1", "3", "-1");

//         when(multipleChoiceQuestionService.getQuestionData(questionId)).thenReturn(question);

//         SuccessResponse successResponse = new SuccessResponse("Question found and retrieved successfully.", HttpStatus.FOUND.value(), question);
//         ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.FOUND);
//         ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.getQuestionById(questionId);

//         assertEquals(expectedResult, response);
//         verify(multipleChoiceQuestionService, times(1)).getQuestionData(questionId);
//     }

//     @Test
//     void testUpdateQuestion() {
//         int questionId = 1;
//         MultipleChoiceQuestion updatedQuestion = new MultipleChoiceQuestion();
//         updatedQuestion.setQuestionId(questionId);
//         // updatedQuestion.setSubcategory("Java");
//         updatedQuestion.setQuestion("Question is a Question");
//         updatedQuestion.setOptionOne("Option_1");
//         updatedQuestion.setOptionTwo("Option_2");
//         updatedQuestion.setOptionThree("Option_3");
//         updatedQuestion.setOptionFour("Option_4");
//         updatedQuestion.setCorrectOption("2");
//         updatedQuestion.setPositiveMark("4");
//         updatedQuestion.setNegativeMark("-1");

//         when(multipleChoiceQuestionService.updateQuestion(questionId, updatedQuestion)).thenReturn(updatedQuestion);

//         SuccessResponse successResponse = new SuccessResponse("Question data updated successfully.", HttpStatus.OK.value(), updatedQuestion);
//         ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.OK);
//         ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.updateQuestion(questionId, updatedQuestion);

//         assertEquals(expectedResult, response);
//         verify(multipleChoiceQuestionService, times(1)).updateQuestion(questionId, updatedQuestion);
//     }

//     @Test
//     void testDeleteQuestionById() {
//         int questionId = 1;
//         doNothing().when(multipleChoiceQuestionService).deleteQuestion(questionId);

//         SuccessResponse successResponse = new SuccessResponse();
//         successResponse.setMessage("Question deleted successfully");
//         successResponse.setStatusCode(HttpStatus.OK.value());
//         ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.OK);
//         ResponseEntity<SuccessResponse> response = multipleChoiceQuestionController.deleteQuestionById(questionId);

//         assertEquals(expectedResult, response);
//         verify(multipleChoiceQuestionService, times(1)).deleteQuestion(questionId);
//     }
// }
