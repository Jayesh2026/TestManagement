package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
    
}
