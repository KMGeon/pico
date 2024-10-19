package com.team5.sparcs.pico.dto.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private String code;
    private String message;
    private String transactionId;
    private Map<String, String> validation;


    public void addValidation(String field, String errorMessage) {
        if (validation == null) {
            validation = new HashMap<>();
        }
        validation.put(field, errorMessage);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", validation=" + validation +
                '}';
    }
}