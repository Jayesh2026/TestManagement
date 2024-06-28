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
import com.example.repository.SubCategoryRepository;
import com.example.service.Impl.SubCategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SubCategoryServiceTest {

    @Mock
    SubCategoryRepository subCategoryRepository;

    @InjectMocks
    SubCategoryServiceImpl subCategoryServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSubCategory() {
        SubCategory actualSubCategory = new SubCategory(1, "SubCategory_1", "SubCategory Details",
                new Category(1, "Category_1", "Category details"));

        when(subCategoryRepository.save(actualSubCategory)).thenReturn(actualSubCategory);
        SubCategory expectSubCategory = subCategoryServiceImpl.saveSubCategory(actualSubCategory);

        verify(subCategoryRepository, times(1)).save(actualSubCategory);
        assertEquals(actualSubCategory, expectSubCategory);
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
        SubCategory existingSubCategory = new SubCategory(subCategoryId, "SubCategory_1", "SubCategory Details",
                new Category(1, "Category_1", "Category details"));

        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(existingSubCategory));
        subCategoryServiceImpl.deleteSubCategory(subCategoryId);

        verify(subCategoryRepository, times(1)).findById(subCategoryId);
        verify(subCategoryRepository, times(1)).deleteById(subCategoryId);
    }
}
