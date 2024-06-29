package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
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

import com.example.model.Category;
import com.example.response.SuccessResponse;
import com.example.service.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category(1, "Category 1", "Category Description");

        when(categoryService.createCategory(category)).thenReturn(category);

        SuccessResponse successResponse = new SuccessResponse("Category created Successfully.",
                HttpStatus.CREATED.value(), category);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        ResponseEntity<SuccessResponse> response = categoryController.createCategory(category);

        assertEquals(expectedResult, response);
    }


    @Test
    public void testGetAllCategory() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Category description 1"));
        categories.add(new Category(2, "Category 2", "Category description 2"));

        when(categoryService.getAllCategories()).thenReturn(categories);

        SuccessResponse expectedResponse = new SuccessResponse("All Categories are retrieved successfully.",
                HttpStatus.OK.value(), categories);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
        ResponseEntity<SuccessResponse> response = categoryController.getAllCategory();

        assertEquals(expectedResult, response);
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testGetCategoryByName() {
        String categoryName = "Category 1";
        Category category = new Category(1, categoryName, "Category description");

        when(categoryService.getCategoryByCategoryName(categoryName)).thenReturn(category);

        SuccessResponse expectedResponse = new SuccessResponse("Category is retrieved successfully.",
                HttpStatus.FOUND.value(), category);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(expectedResponse, HttpStatus.FOUND);
        ResponseEntity<SuccessResponse> response = categoryController.getCategoryByName(categoryName);

        assertEquals(expectedResult, response);
        verify(categoryService, times(1)).getCategoryByCategoryName(categoryName);
    }

    @Test
    public void testUpdateCategory() {
        Integer categoryId = 1;
        Category updatedCategory = new Category(categoryId, "Updated Category Name", "Updated Category Description");

        when(categoryService.updateCategory(categoryId, updatedCategory)).thenReturn(updatedCategory);
        SuccessResponse expectedResponse = new SuccessResponse("Category updated successfully.", HttpStatus.OK.value(), updatedCategory);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
        ResponseEntity<SuccessResponse> response = categoryController.updateCategory(categoryId, updatedCategory);

        assertEquals(expectedResult, response);
        verify(categoryService, times(1)).updateCategory(categoryId, updatedCategory);
    }

    @Test
    public void testDeleteCategoryByName() {
        Integer categoryId = 1;
        doNothing().when(categoryService).deleteCategoryById(categoryId);

        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Category deleted successfully");
        successResponse.setStatusCode(HttpStatus.OK.value());
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.OK);
        ResponseEntity<SuccessResponse> response = categoryController.deleteCategoryById(categoryId);

        assertEquals(expectedResult, response);
        verify(categoryService, times(1)).deleteCategoryById(categoryId);
    }

}
