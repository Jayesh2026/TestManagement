package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.model.Category;
import com.example.repository.CategoryRepository;
import com.example.service.Impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        Category actualCategory = new Category(1, "Category 1", "Category is a category");

        when(categoryRepository.save(actualCategory)).thenReturn(actualCategory);
        Category expectedCategory = categoryServiceImpl.createCategory(actualCategory);

        verify(categoryRepository, times(1)).save(actualCategory);
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Category description 1"));
        categories.add(new Category(2, "Category 2", "Category description 2"));

        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> returnedCategories = categoryServiceImpl.getAllCategories();
        verify(categoryRepository, times(1)).findAll();

        assertEquals(categories.size(), returnedCategories.size());
    }

    @Test
    public void testGetCategoryByCategoryName() {
        String categoryName = "Category 1";
        Category category = new Category(1, categoryName, "Category description");

        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName)).thenReturn(Optional.of(category));
        Category returnedCategory = categoryServiceImpl.getCategoryByCategoryName(categoryName);
        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase(categoryName);

        assertEquals(category, returnedCategory);
    }

    @Test
    public void testDeleteCategoryById() {
        int categoryId = 1;
        Category category = new Category(categoryId, "Category 1", "Category description");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        doNothing().when(categoryRepository).deleteById(categoryId);

        categoryServiceImpl.deleteCategoryById(categoryId);

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    public void testUpdateCategory() {
        int categoryId = 1;
        Category existingCategory = new Category(categoryId, "Category 1", "Category description");
        Category updatedCategory = new Category(categoryId, "Updated Category", "Updated category description");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findByCategoryNameIgnoreCase(updatedCategory.getCategoryName())).thenReturn(Optional.empty());
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);

        Category returnedCategory = categoryServiceImpl.updateCategory(categoryId, updatedCategory);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase(updatedCategory.getCategoryName());
        verify(categoryRepository, times(1)).save(existingCategory);
        assertEquals(updatedCategory, returnedCategory);
    }

}
