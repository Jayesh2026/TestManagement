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
import com.example.model.SubCategory;
import com.example.response.SuccessResponse;
import com.example.service.Impl.SubCategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SubCategoryControllerTest {

    @Mock
    SubCategoryServiceImpl subCategoryServiceImpl;

    @InjectMocks
    SubcategoryController subCategoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSubCategory() {
        SubCategory subCategory = new SubCategory(1, "SubCategory_1", "SubCategory Details",
                new Category(1, "Category_1", "Category details"));

        when(subCategoryServiceImpl.saveSubCategory(subCategory)).thenReturn(subCategory);

        SuccessResponse successResponse = new SuccessResponse("SubCategory created Successfully.",
                HttpStatus.CREATED.value(), subCategory);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse,
                HttpStatus.CREATED);
        ResponseEntity<SuccessResponse> response = subCategoryController.saveSubCategory(subCategory);

        assertEquals(expectedResult, response);
    }

    @Test
    public void testGetAllSubCategories_Success() {
        List<SubCategory> subCategories = new ArrayList<>();
        subCategories.add(new SubCategory(1, "SubCategory_1", "SubCategory Details",
                new Category(1, "Category_1", "Category details")));
        subCategories.add(new SubCategory(2, "SubCategory_2", "Another SubCategory",
                new Category(2, "Category_2", "Category details")));

        when(subCategoryServiceImpl.getAllSubCategories()).thenReturn(subCategories);

        SuccessResponse successResponse = new SuccessResponse("All SubCategory retrieved Successfully.",
                HttpStatus.OK.value(), subCategories);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.OK);
        ResponseEntity<SuccessResponse> response = subCategoryController.getAllSubCategories();

        assertEquals(expectedResult, response);
    }

    @Test
    public void testGetSubCategoryById_Success() {
        SubCategory subCategory = new SubCategory(1, "SubCategory_1", "SubCategory Details",
                new Category(1, "Category_1", "Category details"));

        when(subCategoryServiceImpl.getSubCategoryById(1)).thenReturn(subCategory);

        SuccessResponse successResponse = new SuccessResponse("SubCategory retrieved Successfully.",
                HttpStatus.OK.value(), subCategory);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.OK);
        ResponseEntity<SuccessResponse> response = subCategoryController.getSubCategoryById(1);

        assertEquals(expectedResult, response);
    }

    @Test
    public void testUpdateSubCategory_Success() {
        SubCategory updatedSubCategory = new SubCategory(1, "Updated_SubCategory",
                "Updated SubCategory Details",
                new Category(1, "Category_1", "Category details"));

        when(subCategoryServiceImpl.updateSubCategory(1, updatedSubCategory)).thenReturn(updatedSubCategory);

        ResponseEntity<SuccessResponse> response = subCategoryController.updateSubCategory(1,
                updatedSubCategory);
        SuccessResponse successResponse = new SuccessResponse("SubCategory updated Successfully.",
                HttpStatus.OK.value(), updatedSubCategory);
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.OK);

        assertEquals(expectedResult, response);
    }

    @Test
    public void testDeleteSubCategory_Success() {
        doNothing().when(subCategoryServiceImpl).deleteSubCategory(1);

        ResponseEntity<SuccessResponse> response = subCategoryController.deleteSubCategory(1);

        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("SubCategory deleted successfully");
        successResponse.setStatusCode(HttpStatus.OK.value());
        ResponseEntity<SuccessResponse> expectedResult = new ResponseEntity<>(successResponse, HttpStatus.OK);

        assertEquals(expectedResult, response);
    }

}
