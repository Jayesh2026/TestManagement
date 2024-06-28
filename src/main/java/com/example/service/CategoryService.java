package com.example.service;

import java.util.List;

import com.example.model.Category;

public interface CategoryService {
    
    public Category createCategory(Category category);

    public List<Category> getAllCategories();

    public Category getCategoryByCategoryName(String categoryName);

    public Category updateCategory(String categoryName, Category category);

    void deleteCategoryByName(String categoryName);
}