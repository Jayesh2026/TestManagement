package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Category;
import com.example.model.SubCategory;
import java.util.Optional;


@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    
    Optional<SubCategory> findBySubcategoryId(Integer subcategoryId);

    Optional<SubCategory> findBySubcategoryNameIgnoreCaseAndCategory(String subcategoryName, Category category);

}

