package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Category;
import com.example.response.SuccessResponse;
import com.example.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.createCategory(category);
        SuccessResponse successResponse = new SuccessResponse("Category created Successfully.",
                HttpStatus.CREATED.value(), savedCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllCategory() {
        List<Category> categoryList = categoryService.getAllCategories();
        SuccessResponse successResponse = new SuccessResponse("All Categories are retrieved successfully.",
                HttpStatus.OK.value(), categoryList);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<SuccessResponse> getCategoryByName(@RequestParam("categoryName") String categoryName) {
        Category category = categoryService.getCategoryByCategoryName(categoryName);
        SuccessResponse successResponse = new SuccessResponse("Category is retrieved successfully.",
                HttpStatus.FOUND.value(), category);
        return new ResponseEntity<>(successResponse, HttpStatus.FOUND);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse> updateCategory(@PathVariable("categoryId") Integer categoryId,
            @RequestBody Category category) {
        Category updateCategory = categoryService.updateCategory(categoryId, category);
        SuccessResponse successResponse = new SuccessResponse("Category updated successfully.", HttpStatus.OK.value(),
                updateCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse> deleteCategoryById(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategoryById(categoryId);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Category deleted successfully");
        successResponse.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
