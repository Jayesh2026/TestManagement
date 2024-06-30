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
import com.example.model.SubCategory;
import com.example.repository.CategoryRepository;
import com.example.repository.SubCategoryRepository;
import com.example.service.Impl.SubCategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SubCategoryServiceTest {

    @Mock
    SubCategoryRepository subCategoryRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    SubCategoryServiceImpl subCategoryServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSubCategory() {
        Category category = new Category(1, "Category_1", "Category details");
        SubCategory subCategory = new SubCategory(1, "SubCategory_1", "SubCategory Details", category);

        when(categoryRepository.findByCategoryIdAndCategoryNameIgnoreCase(category.getCategoryId(), category.getCategoryName())).thenReturn(Optional.of(category));
        when(subCategoryRepository.findBySubcategoryNameIgnoreCaseAndCategory(subCategory.getSubcategoryName(), category))
                                                                .thenReturn(Optional.empty()); // No existing subcategory with the same name
        when(subCategoryRepository.save(subCategory)).thenReturn(subCategory);

        SubCategory savedSubCategory = subCategoryServiceImpl.saveSubCategory(subCategory);

        verify(categoryRepository, times(1)).findByCategoryIdAndCategoryNameIgnoreCase(category.getCategoryId(), category.getCategoryName());
        verify(subCategoryRepository, times(1)).findBySubcategoryNameIgnoreCaseAndCategory(subCategory.getSubcategoryName(), category);
        verify(subCategoryRepository, times(1)).save(subCategory);

        assertEquals(subCategory, savedSubCategory);
    }

    @Test
    public void testGetAllSubCategories() {
        List<SubCategory> subCategories = new ArrayList<>();
        subCategories.add(new SubCategory(1, "SubCategory_1", "SubCategory Details",
                new Category(1, "Category_1", "Category details")));
        subCategories.add(new SubCategory(2, "SubCategory_2", "SubCategory Details",
                new Category(2, "Category_2", "Category details")));

        when(subCategoryRepository.findAll()).thenReturn(subCategories);
        List<SubCategory> retrievedSubCategories = subCategoryServiceImpl.getAllSubCategories();

        verify(subCategoryRepository, times(1)).findAll();
        assertEquals(subCategories, retrievedSubCategories);
    }

    @Test
    public void testGetSubCategoryById() {
        SubCategory expectedSubCategory = new SubCategory(1, "SubCategory_1", "SubCategory Details",
                new Category(1, "Category_1", "Category details"));

        when(subCategoryRepository.findBySubcategoryId(1)).thenReturn(Optional.of(expectedSubCategory));
        SubCategory retrievedSubCategory = subCategoryServiceImpl.getSubCategoryById(1);

        verify(subCategoryRepository, times(1)).findBySubcategoryId(1);
        assertEquals(expectedSubCategory, retrievedSubCategory);
    }

    @Test
    public void testUpdateSubCategory() {
        Integer subCategoryId = 1;
        SubCategory existingSubCategory = new SubCategory(subCategoryId, "SubCategory_1", "SubCategory Details",
                new Category(1, "Category_1", "Category details"));

        SubCategory updatedSubCategory = new SubCategory(subCategoryId, "Updated_SubCategory", "Updated Details",
                new Category(1, "Category_1", "Category details"));

        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(existingSubCategory));
        when(subCategoryRepository.save(existingSubCategory)).thenReturn(updatedSubCategory);
        SubCategory returnedSubCategory = subCategoryServiceImpl.updateSubCategory(subCategoryId, updatedSubCategory);

        verify(subCategoryRepository, times(1)).findById(subCategoryId);
        verify(subCategoryRepository, times(1)).save(existingSubCategory);

        assertEquals(updatedSubCategory, returnedSubCategory);
    }

    @Test
    public void testDeleteSubCategory() {
        Integer subCategoryId = 1;
        SubCategory subCategory = new SubCategory(subCategoryId, "SubCategory_1", "SubCategory Details", new Category());

        when(subCategoryRepository.findBySubcategoryId(subCategoryId)).thenReturn(Optional.of(subCategory));
        doNothing().when(subCategoryRepository).deleteById(subCategoryId);
        subCategoryServiceImpl.deleteSubCategory(subCategoryId);

        verify(subCategoryRepository, times(1)).findBySubcategoryId(subCategoryId);
        verify(subCategoryRepository, times(1)).deleteById(subCategoryId);
    }
}
