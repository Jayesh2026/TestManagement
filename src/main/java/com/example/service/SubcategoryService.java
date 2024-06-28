package com.example.service;

import java.util.List;

import com.example.model.SubCategory;

public interface SubCategoryService {

    public SubCategory saveSubCategory(SubCategory subCategory);

    public List<SubCategory> getAllSubCategories();

    public SubCategory getSubCategoryById(Integer id);

    public SubCategory updateSubCategory(Integer id, SubCategory subCategory);

    public void deleteSubCategory(Integer id);
    
}