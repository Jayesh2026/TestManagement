package com.example.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.CategoryNotFoundException;
import com.example.model.SubCategory;
import com.example.repository.SubCategoryRepository;
import com.example.service.SubCategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    public SubCategory saveSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    public SubCategory getSubCategoryById(Integer id) {
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findBySubcategoryId(id);
        if(optionalSubCategory.isPresent()){
            return  optionalSubCategory.get();
        }
        else return null;
    }

    public SubCategory updateSubCategory(Integer id, SubCategory subCategory) {
        Optional<SubCategory> optionalExistingSubCategory = subCategoryRepository.findById(id);
        if (optionalExistingSubCategory.isPresent()) {
            SubCategory existingSubCategory = optionalExistingSubCategory.get();
    
            if (subCategory.getSubcategoryName() != null) {
                existingSubCategory.setSubcategoryName(subCategory.getSubcategoryName());
            }
            if (subCategory.getSubcategoryDescription() != null) {
                existingSubCategory.setSubcategoryDescription(subCategory.getSubcategoryDescription());
            }
            if (subCategory.getCategory() != null) {
                existingSubCategory.setCategory(subCategory.getCategory());
            }
    
            return subCategoryRepository.save(existingSubCategory);
        } else {
            throw new NullPointerException("SubCategory with id " + id + " not found");
        }
    }
    
    public void deleteSubCategory(Integer subCategoryId) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(subCategoryId);
    
        if (subCategory.isPresent()) {
            subCategoryRepository.deleteById(subCategoryId);
        } else {
            throw new CategoryNotFoundException("SubCategory with id " + subCategoryId + " not found");
        }
    }
    
}
