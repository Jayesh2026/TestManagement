package com.example.service;

import java.util.List;

import com.example.model.Category;

public interface CategoryService {
    
    public Category createCategory(Category category);

    public List<Category> getAllCategories();

    public Category getCategoryByIdOrCategoryName(Category category);

    public Category updateCategory(Category category);

    public void deleteCategoryById(Integer categoryId);
}