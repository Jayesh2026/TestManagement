package com.example.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.DataNotFoundException;
import com.example.exception.DuplicateDataException;
import com.example.model.Category;
import com.example.model.SubCategory;
import com.example.repository.CategoryRepository;
import com.example.repository.SubCategoryRepository;
import com.example.service.SubCategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public SubCategory saveSubCategory(SubCategory subCategory) {
        Integer categoryId = subCategory.getCategory().getCategoryId();
        String categoryName = subCategory.getCategory().getCategoryName();
        String subcategoryName = subCategory.getSubcategoryName();
    
        // Check if the category exists based on categoryId and categoryName
        Category category = categoryRepository.findByCategoryIdAndCategoryNameIgnoreCase(categoryId, categoryName)
                .orElseThrow(() -> new DataNotFoundException(
                        "Category with Id " + categoryId + " and name '" + categoryName + "' not found or Id and name are not associated with each other."));
    
        // Check if a subcategory with the same name and category exists
        subCategoryRepository.findBySubcategoryNameIgnoreCaseAndCategory(subcategoryName, category)
                .ifPresent(existingSubCategory -> {
                    throw new DuplicateDataException(
                            "Subcategory with name '" + subcategoryName + "' already exists in '" + category.getCategoryName() + "' category.");
                });
    
        subCategory.setCategory(category);
        return subCategoryRepository.save(subCategory);
    }
    
    @Override
    public List<SubCategory> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        if (subCategories == null || subCategories.isEmpty()) {
            throw new DataNotFoundException("Unable to retrieved all SubCategories, Not found any SubCategories.");
        }
        return subCategories;
    }

    @Override
    public SubCategory getSubCategoryById(Integer id) {
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findBySubcategoryId(id);
        if(!subCategoryOptional.isPresent()){
            throw new DataNotFoundException("Id of '" + id + "' SubCategory is not present.");
        }
        return subCategoryOptional.get();
    }

    @Override
    public SubCategory updateSubCategory(Integer id, SubCategory subCategoryUpdate) {
        Optional<SubCategory> optionalExistingSubCategory = subCategoryRepository.findById(id);
        if (optionalExistingSubCategory.isPresent()) {
            SubCategory existingSubCategory = optionalExistingSubCategory.get();

            if (subCategoryUpdate.getSubcategoryName() != null) {
                existingSubCategory.setSubcategoryName(subCategoryUpdate.getSubcategoryName());
            }
            if (subCategoryUpdate.getSubcategoryDescription() != null) {
                existingSubCategory.setSubcategoryDescription(subCategoryUpdate.getSubcategoryDescription());
            }
            if (subCategoryUpdate.getCategory() != null) {
                existingSubCategory.setCategory(subCategoryUpdate.getCategory());
            }

            return subCategoryRepository.save(existingSubCategory);
        } else {
            throw new DataNotFoundException("SubCategory with id " + id + " not found");
        }
    }

    @Override
    public void deleteSubCategory(Integer subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findBySubcategoryId(subCategoryId)
                .orElseThrow(() -> new DataNotFoundException("SubCategory with id " + subCategoryId + " not found"));

        // Remove the association with Category to avoid foreign key constraint violation
        subCategory.setCategory(null);
        subCategoryRepository.deleteById(subCategoryId);
    }

}
