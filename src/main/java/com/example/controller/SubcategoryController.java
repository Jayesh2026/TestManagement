package com.example.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.model.SubCategory;
import com.example.response.SuccessResponse;
import com.example.service.SubCategoryService;

@RestController
@RequestMapping("/api/subCategory")
public class SubcategoryController {

    @Autowired
    SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<SuccessResponse> saveSubCategory(@RequestBody SubCategory subCategory) {
        SubCategory savedSubCategory = subCategoryService.saveSubCategory(subCategory);
        SuccessResponse successResponse = new SuccessResponse("SubCategory created Successfully.",
                HttpStatus.CREATED.value(), savedSubCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<SuccessResponse> getAllSubCategories() {
        List<SubCategory> subCategoriesList = subCategoryService.getAllSubCategories();
        SuccessResponse successResponse = new SuccessResponse("All SubCategory retrieved Successfully.",
                HttpStatus.OK.value(), subCategoriesList);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<SuccessResponse> getSubCategoryById(@PathVariable("subCategoryId") Integer subCategoryId) {
        SubCategory subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        SuccessResponse successResponse = new SuccessResponse("SubCategory retrieved Successfully.",
                HttpStatus.OK.value(), subCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{subCategoryId}")
    public ResponseEntity<SuccessResponse> updateSubCategory(@PathVariable("subCategoryId") Integer subCategoryId,
            @RequestBody SubCategory subCategory) {
        SubCategory updatedSubCategory = subCategoryService.updateSubCategory(subCategoryId, subCategory);
        SuccessResponse successResponse = new SuccessResponse("SubCategory retrieved Successfully.",
                HttpStatus.OK.value(), updatedSubCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<SuccessResponse> deleteSubCategory(@PathVariable("subCategoryId") Integer subCategoryId) {
        subCategoryService.deleteSubCategory(subCategoryId);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("SubCategory deleted successfully");
        successResponse.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
