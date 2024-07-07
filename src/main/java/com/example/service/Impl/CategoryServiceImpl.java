package com.example.service.Impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.DataNotFoundException;
import com.example.exception.DuplicateDataException;
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
        logger.info("Saving category with name: {}", category.getCategoryName());

        if (categoryRepository.existsByCategoryNameIgnoreCase(category.getCategoryName())) {
            throw new DuplicateDataException("Category '" + category.getCategoryName() + "' already exists");
        }

        Category savedCategory = categoryRepository.save(category);
        logger.info("Category Saved: {}", savedCategory.getCategoryName());
        return savedCategory;
    }

    @Override
    public List<Category> getAllCategories() {
        logger.info("Fetching all categories.");
        List<Category> categories = categoryRepository.findAll();
        
        if (categories.isEmpty()) {
            throw new DataNotFoundException("Not found any category.");
        }
    
        logger.info("Categories fetched: {}", categories.size());
        return categories;
    }
    

    @Override
    public Category getCategoryByCategoryName(String categoryName) {
        Optional<Category> categoryData = categoryRepository.findByCategoryNameIgnoreCase(categoryName);
        if(!categoryData.isPresent()){
            String message = "Category '"+ categoryName +"' is not present in database.";
            logger.error(message);
            throw new DataNotFoundException(message);
        }
        return categoryData.get();
    }

    @Override
    public Category updateCategory(Integer categoryId, Category updatedCategory) {

        Category existingCategoryData = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> {
                                    String message = "Category with id '"+ categoryId +"' is not present in database.";
                                    logger.error(message);
                                    return new DataNotFoundException(message);
                                });

        if (!existingCategoryData.getCategoryName().equalsIgnoreCase(updatedCategory.getCategoryName())) {
            Optional<Category> categoryByNameOptional = categoryRepository.findByCategoryNameIgnoreCase(updatedCategory.getCategoryName());
            
            if (categoryByNameOptional.isPresent()) {
                String message = "Category with name '" + updatedCategory.getCategoryName() + "' already exists,  along with Id number: " +categoryByNameOptional.get().getCategoryId();
                logger.error(message);
                throw new DuplicateDataException(message);
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
