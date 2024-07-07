package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer categoryId;

    @NotEmpty(message = "Category name cannot be null")
    @Size(max = 100, message = "Category name must be less than or equal to 100 characters.")
    String categoryName;

    @Size(max = 250, message = "Category description must be less than or equal to 250 characters.")
    String categoryDescription;
}
