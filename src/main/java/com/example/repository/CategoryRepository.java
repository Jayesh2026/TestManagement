package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Category;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByCategoryNameIgnoreCase(String categoryName);

    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);

    void deleteByCategoryName(String categoryName);

    Optional<Category> findByCategoryIdAndCategoryNameIgnoreCase(Integer categoryId, String categoryName);
}
