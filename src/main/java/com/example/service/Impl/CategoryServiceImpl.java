package com.example.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.ObjectIsNullException;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        if(category == null){
            throw new ObjectIsNullException("Category should not be null");
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public Category getCategoryByIdOrCategoryName(Category category) {
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        
    }
    
}
