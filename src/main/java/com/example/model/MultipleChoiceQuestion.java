package com.example.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mcq_question")
public class MultipleChoiceQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer questionId;

    @NotNull(message = "Question cannot be null")
    @Size(max = 250, message = "Question must be less than or equal to 250 characters")
    String question;

    @NotBlank(message = "Option one can not be blank")
    @Size(max = 100, message = "Option one must be less than or equal to 100 characters")
    String optionOne;

    @NotBlank(message = "Option two can not be blank")
    @Size(max = 100, message = "Option two must be less than or equal to 100 characters")
    String optionTwo;

    @NotBlank(message = "Option three can not be blank")
    @Size(max = 100, message = "Option three must be less than or equal to 100 characters")
    String optionThree;

    @NotBlank(message = "Option four can not be blank")
    @Size(max = 100, message = "Option four must be less than or equal to 100 characters")
    String optionFour;

    @NotBlank(message = "Correct option can not be blank")
    String correctOption;

    @NotNull(message = "Positive mark must not be null")
    String positiveMark;

    @NotNull(message = "Negative mark must not be null")
    String negativeMark;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subcategory_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    SubCategory subcategory;
}