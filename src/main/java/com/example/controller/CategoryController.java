package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Category;
import com.example.model.MultipleChoiceQuestion;
import com.example.response.SuccessResponse;
import com.example.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<SuccessResponse> createCategory(@RequestBody Category category){
        Category savedCategory = categoryService.createCategory(category);
        SuccessResponse successResponse = new SuccessResponse("Category created Successfully.", HttpStatus.CREATED.value(), savedCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<SuccessResponse> getAllCategory(){
        List<Category> categoryList = categoryService.getAllCategories();
        SuccessResponse successResponse = new SuccessResponse("All Categories are retrieved successfully.", HttpStatus.OK.value(), categoryList);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    public ResponseEntity<SuccessResponse> getCategoryByName(@RequestBody String categoryName){
        Category category = categoryService.getCategoryByCategoryName(categoryName);
        SuccessResponse successResponse = new SuccessResponse("All Categories are retrieved successfully.", HttpStatus.FOUND.value(), category);
        return new ResponseEntity<>(successResponse, HttpStatus.FOUND);
    }

}
