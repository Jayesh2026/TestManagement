package com.example.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.CategoryNotFoundException;
import com.example.exception.DuplicateCategoryException;
import com.example.exception.IllegalArgumentException;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category should not be null");
        }

        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new DuplicateCategoryException("Category with name '" + category.getCategoryName() + "' already exists");
        }

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories == null || categories.isEmpty()) {
            throw new CategoryNotFoundException("Unable to retrieved all categories, Not found any question.");
        }
        return categories;
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) {
        Optional<Category> categoryData = categoryRepository.findByCategoryName(categoryName);
        if(!categoryData.isPresent()){
            throw new CategoryNotFoundException("Name of '" + categoryName + "' category is not present.");
        }
        return categoryData.get();
    }

    @Override
    public Category updateCategory(Integer categoryId, Category updatedCategory) {
        Optional<Category> exitingCategory = categoryRepository.findById(categoryId);
        if(!exitingCategory.isPresent()){
            throw new CategoryNotFoundException("Category with name " + categoryId + " not found");
        }

        Category existingCategoryData = exitingCategory.get();

        if (!existingCategoryData.getCategoryName().equals(updatedCategory.getCategoryName())) {
            Optional<Category> categoryByNameOptional = categoryRepository.findByCategoryName(updatedCategory.getCategoryName());
            if (categoryByNameOptional.isPresent()) {
                throw new IllegalArgumentException("Category with name '" + updatedCategory.getCategoryName() + "' already exists");
            }
        }

        // Update fields conditionally
        if (updatedCategory.getCategoryName() != null) {
            existingCategoryData.setCategoryName(updatedCategory.getCategoryName());
        }
        if (updatedCategory.getCategoryDescription() != null) {
            existingCategoryData.setCategoryDescription(updatedCategory.getCategoryDescription());
        }

        return categoryRepository.save(existingCategoryData);
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new CategoryNotFoundException("Category with Id " + categoryId + " not found");
        }
    }
}
