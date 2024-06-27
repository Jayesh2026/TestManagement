package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.response.ErrorResponse;

@ControllerAdvice
public class MCQGlobalException {
    
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleQuestionNotFoundException(QuestionNotFoundException questionException){
        ErrorResponse errorResponse = new ErrorResponse(questionException.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ObjectIsNullException.class)
    public ResponseEntity<ErrorResponse> handleObjectIsNullException(QuestionNotFoundException objectNullException){
        ErrorResponse errorResponse = new ErrorResponse(objectNullException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
