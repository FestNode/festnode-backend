package com.festnode.festnode.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timeStamp;
    private String errorMessage;
    private String errorDetails;
    private String errorCode;

    public ErrorResponse(String errorMessage, String errorDetails, String errorCode) {
        this.timeStamp = LocalDateTime.now();
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
        this.errorCode = errorCode;
    }
}
