package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.model.Category;
import com.example.model.MultipleChoiceQuestion;
import com.example.model.SubCategory;
import com.example.repository.CategoryRepository;
import com.example.repository.MultipleChoiceQuestionRepository;
import com.example.repository.SubCategoryRepository;
import com.example.service.Impl.MultipleChoiceQuestionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MultipleChoiceQuestionServiceTest {

    @Mock
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    SubCategoryRepository subCategoryRepository;

    @InjectMocks
    MultipleChoiceQuestionServiceImpl multipleChoiceQuestionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveQuestion() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(1, "Question", "OptionA",
        "OptionB", "OptionC", "OptionD", "1", "3", "-1", new SubCategory(1, "SubCategory Name", "SubCategory Description", 
        new Category(1, "Category Name", "Category Description")));

        when(multipleChoiceQuestionRepository.save(question)).thenReturn(question);
        MultipleChoiceQuestion savedQuestion = multipleChoiceQuestionService.saveQuestion(question);
        verify(multipleChoiceQuestionRepository, times(1)).save(question);

        assertEquals(question, savedQuestion);
    }

    @Test
    public void testGetAllQuestions() {
        List<MultipleChoiceQuestion> questionList = new ArrayList<>();
        questionList.add(new MultipleChoiceQuestion(1, "save question 1", "option A", "option B", "option C",
        "option D", "option A", "3", "-1",  new SubCategory(1, "SubCategory Name", "SubCategory Description", 
        new Category(1, "Category Name", "Category Description"))));
questionList.add(new MultipleChoiceQuestion(1,  "save question 2", "option A1", "option B1", "option C1",
        "option D1", "option A1", "3", "-1", new SubCategory(1, "SubCategory Name", "SubCategory Description", 
        new Category(1, "Category Name", "Category Description"))));

        when(multipleChoiceQuestionRepository.findAll()).thenReturn(questionList);
        List<MultipleChoiceQuestion> fetchedList = multipleChoiceQuestionService.getAllQuestionsData();
        assertEquals(questionList, fetchedList);
    }

    @Test
    public void testGetQuestionDataById() {
        Integer questionId = 1;
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setQuestionId(questionId);

        when(multipleChoiceQuestionRepository.findById(questionId)).thenReturn(Optional.of(question));
        MultipleChoiceQuestion questionRetrieved = multipleChoiceQuestionService.getQuestionData(questionId);
        verify(multipleChoiceQuestionRepository).findById(questionId);

        assertEquals(question, questionRetrieved);
    }

    @Test
    public void testDeleteQuestion() {
        Integer questionId = 5;
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        when(multipleChoiceQuestionRepository.findById(questionId)).thenReturn(Optional.of(question));

        multipleChoiceQuestionService.deleteQuestion(questionId);

        verify(multipleChoiceQuestionRepository).findById(questionId);
        verify(multipleChoiceQuestionRepository).deleteById(questionId);
    }

    @Test
    void testUpdateQuestion() {
        Integer questionId = 1;
        MultipleChoiceQuestion existingQuestion = (new MultipleChoiceQuestion(questionId, "save question 1", "option A", "option B", "option C",
        "option D", "option A", "3", "-1",  new SubCategory(1, "SubCategory Name", "SubCategory Description", 
        new Category(1, "Category Name", "Category Description"))));

        MultipleChoiceQuestion updatedQuestionData = (new MultipleChoiceQuestion(questionId, "save question 1", "option A", "option B", "option C",
        "option D", "option A", "3", "-1",  new SubCategory(2, "Java", "SubCategory Description",
        new Category(1, "Category Name", "Category Description"))));

        when(multipleChoiceQuestionRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));
        when(multipleChoiceQuestionRepository.save(updatedQuestionData)).thenReturn(updatedQuestionData);
        MultipleChoiceQuestion updatedQuestion = multipleChoiceQuestionService.updateQuestion(questionId, updatedQuestionData);

        assertEquals(updatedQuestionData, updatedQuestion);

        verify(multipleChoiceQuestionRepository, times(1)).findById(questionId);
        verify(multipleChoiceQuestionRepository, times(1)).save(updatedQuestionData);
    }


}
