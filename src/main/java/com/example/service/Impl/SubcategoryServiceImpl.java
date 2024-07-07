package com.example.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(SubCategoryServiceImpl.class);

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public SubCategory saveSubCategory(SubCategory subCategory) {

        Category category = categoryRepository.findById(subCategory.getCategory().getCategoryId())
                .orElseThrow(() -> {
                    String message = "Category with ID " + subCategory.getCategory().getCategoryId() + " not found.";
                    logger.error(message);
                    return new DataNotFoundException(message);
                });
                    
    
        if (!category.getCategoryName().equalsIgnoreCase(subCategory.getCategory().getCategoryName())) {
            String message = "Category name '" + subCategory.getCategory().getCategoryName() + "' does not match with ID " + subCategory.getCategory().getCategoryId() + ".";
            logger.error(message);
            throw new DataNotFoundException(message);
        }
    
        // Check if a subcategory with the same name already exists in the same category
        Optional<SubCategory> existingSubCategory = subCategoryRepository.findBySubcategoryNameIgnoreCaseAndCategory(
                subCategory.getSubcategoryName(), category);
    
        if (existingSubCategory.isPresent()) {
            String message = "Subcategory with name '" + subCategory.getSubcategoryName() + "' already exists in category '" + category.getCategoryName() + "'.";
            logger.error(message);
            throw new DuplicateDataException(message);
        }
    
        subCategory.setCategory(category);
    
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        logger.info("Subcategory '{}' saved successfully in category '{}'.", savedSubCategory.getSubcategoryName(), category.getCategoryName());
        return savedSubCategory;
    }
    

    @Override
    public List<SubCategory> getAllSubCategories() {
        logger.info("Fetching all Subcategories.");
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        if (subCategories.isEmpty()) {
            String message = "Not found any SubCategories in database.";
            logger.error(message);
            throw new DataNotFoundException(message);
        }
        
        logger.info("SubCategories fetched: {}", subCategories.size());
        return subCategories;
    }
    

    @Override
    public SubCategory getSubCategoryById(Integer subCategoryId) {
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findBySubcategoryId(subCategoryId);
        if (!subCategoryOptional.isPresent()) {
            String message = "Id of '" + subCategoryId + "' SubCategory is not present.";
            logger.error(message);
            throw new DataNotFoundException(message);
        }
        return subCategoryOptional.get();
    }

    @Override
    public SubCategory updateSubCategory(Integer subCategoryId, SubCategory subCategoryUpdate) {
        Optional<SubCategory> optionalExistingSubCategory = subCategoryRepository.findById(subCategoryId);
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
            SubCategory updatedSubCategory = subCategoryRepository.save(existingSubCategory);
            logger.info("Subcategory updated of Id: {}", subCategoryId);
            return updatedSubCategory;
        } else {
            String message = "SubCategory with id: " + subCategoryId + " not found";
            logger.error(message);
            throw new DataNotFoundException(message);
        }
    }

    @Override
    public void deleteSubCategory(Integer subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findBySubcategoryId(subCategoryId)
                .orElseThrow(() -> new DataNotFoundException("SubCategory with id " + subCategoryId + " not found"));

        // Remove the association with Category to avoid foreign key constraint violation
        subCategory.setCategory(null);
        subCategoryRepository.deleteById(subCategoryId);
        logger.info("SubCategory deleted of Id: {}", subCategoryId);
    }

}
