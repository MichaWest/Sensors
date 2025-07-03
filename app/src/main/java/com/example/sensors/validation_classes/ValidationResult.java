package com.example.sensors.validation_classes;

public class ValidationResult {
    private final boolean valid;
    private final Integer errorMessage;

    private ValidationResult(boolean valid, Integer errorMessage){
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid(){
        return valid;
    }

    public static ValidationResult valid(){
        return new ValidationResult(true, null);
    }

    public static ValidationResult invalid(Integer errorMessage){
        return new ValidationResult(false, errorMessage);
    }

    public Integer getErrorMessage(){
        return errorMessage;
    }
}
