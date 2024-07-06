package com.example.service.Impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        if (category == null) {
            logger.error("Category should not be null.");
            throw new IllegalArgumentException("Category should not be null");
        }

        if (categoryRepository.existsByCategoryNameIgnoreCase(category.getCategoryName())) {
            throw new DuplicateDataException("Category '" + category.getCategoryName() + "' already exists");
        }

        Category savedCategory = categoryRepository.save(category);
        logger.info("Category Saved: {}", savedCategory.getCategoryName());
        return savedCategory;
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            logger.info("Fetching all categories.");
            List<Category> categories = categoryRepository.findAll();
    
            if (categories == null || categories.isEmpty()) {
                throw new DataNotFoundException("Not found any category.");
            }
    
            logger.info("Categories fetched: {}", categories.size());
            return categories;
        } catch (Exception ex) {
            logger.error("Error occurred while fetching categories: {}", ex.getMessage());
            throw new DataNotFoundException("Unable to fetch and retrieved all categories, Not found any category.");
        }
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) {
        Optional<Category> categoryData = categoryRepository.findByCategoryNameIgnoreCase(categoryName);
        if(!categoryData.isPresent()){
            throw new DataNotFoundException("This category is not present in database.");
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
        Category categoryUpdated = categoryRepository.save(existingCategoryData);
        logger.info("Category updated of Id: {}", categoryId);
        return categoryUpdated;
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            throw new DataNotFoundException("Category with Id " + categoryId + " not found");
        }
        categoryRepository.deleteById(categoryId);
        logger.info("Category deleted of Id: {}", categoryId);
    }
}
