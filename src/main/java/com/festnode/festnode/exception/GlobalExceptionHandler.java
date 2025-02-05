package com.festnode.festnode.exception;

import com.festnode.festnode.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateFestException.class)
    public ResponseEntity<ErrorResponse> duplicateFestExceptionHandler(DuplicateFestException dupFestEx, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(dupFestEx.getMessage(), webRequest.getDescription(false), "DUPLICATE FEST DATA FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(EntityNotFoundException entityNotFoundEx, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(entityNotFoundEx.getMessage(), webRequest.getDescription(false), "FEST NOT FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotPermittedException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(NotPermittedException notPermittedEx, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(notPermittedEx.getMessage(), webRequest.getDescription(false), "NOT PERMITTED");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }
}
