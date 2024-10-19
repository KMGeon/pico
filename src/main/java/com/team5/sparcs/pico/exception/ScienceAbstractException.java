package com.team5.sparcs.pico.exception;

import java.util.HashMap;
import java.util.Map;

public class ScienceAbstractException extends RuntimeException {
    public final Map<String, String> validation = new HashMap<>();
    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}

