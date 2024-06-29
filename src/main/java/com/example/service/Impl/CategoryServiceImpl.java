package com.example.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.DataNotFoundException;
import com.example.exception.DuplicateDataException;
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

        if (categoryRepository.existsByCategoryNameIgnoreCase(category.getCategoryName())) {
            throw new DuplicateDataException("Category with name '" + category.getCategoryName() + "' already exists");
        }

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories == null || categories.isEmpty()) {
            throw new DataNotFoundException("Unable to retrieved all categories, Not found any category.");
        }
        return categories;
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) {
        Optional<Category> categoryData = categoryRepository.findByCategoryNameIgnoreCase(categoryName);
        if(!categoryData.isPresent()){
            throw new DataNotFoundException("Name of '" + categoryName + "' category is not present.");
        }
        return categoryData.get();
    }

    @Override
    public Category updateCategory(Integer categoryId, Category updatedCategory) {
        Optional<Category> existingCategoryOptional = categoryRepository.findById(categoryId);
    
        Category existingCategoryData = existingCategoryOptional.orElseThrow(() ->
                                                                    new DataNotFoundException("Category with Id " + categoryId + " not found"));
    
        if (!existingCategoryData.getCategoryName().equalsIgnoreCase(updatedCategory.getCategoryName())) {
            Optional<Category> categoryByNameOptional = categoryRepository.findByCategoryNameIgnoreCase(updatedCategory.getCategoryName());
            if (categoryByNameOptional.isPresent()) {
                throw new DuplicateDataException("Category with name '" + updatedCategory.getCategoryName() + "' already exists, along with Id number: " +categoryByNameOptional.get().getCategoryId());
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
            throw new DataNotFoundException("Category with Id " + categoryId + " not found");
        }
    }
}
