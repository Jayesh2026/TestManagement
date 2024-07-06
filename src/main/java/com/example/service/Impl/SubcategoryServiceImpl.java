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

        // Check if the category exists based on categoryId and categoryName
        Category category = categoryRepository.findByCategoryIdAndCategoryNameIgnoreCase(subCategory.getCategory().getCategoryId(),
                                                                                    subCategory.getCategory().getCategoryName())
                .orElseThrow(() -> new DataNotFoundException(
                        "Given Category 'Id' and 'Category Name' are not found OR Id and name are not associated with each other."));

        // Check if a subcategory with the same name and in the same category exists
        subCategoryRepository.findBySubcategoryNameIgnoreCaseAndCategory(subCategory.getSubcategoryName(), category)
                .ifPresent(existingSubCategory -> {
                    throw new DuplicateDataException(
                            "Subcategory with name '" + subCategory.getSubcategoryName() + "' already exists in '"+ category.getCategoryName() + "' category.");
                });

        subCategory.setCategory(category);
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);

        return savedSubCategory;
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        try{
            logger.info("Fetching all Subcategories.");
            List<SubCategory> subCategories = subCategoryRepository.findAll();
            if (subCategories == null || subCategories.isEmpty()) {
                throw new DataNotFoundException("Not found any SubCategories.");
            }
            logger.info("SubCategories fetched: {}", subCategories.size());
            return subCategories;

        }catch (Exception ex) {
            logger.error("Error occurred while fetching Subcategories: {}", ex.getMessage());
            throw new DataNotFoundException("Unable to fetch and retrieved all Subcategories");
        }
    }

    @Override
    public SubCategory getSubCategoryById(Integer id) {
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findBySubcategoryId(id);
        if (!subCategoryOptional.isPresent()) {
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
        logger.info("SubCategory deleted of Id: {}", subCategoryId);
    }

}
