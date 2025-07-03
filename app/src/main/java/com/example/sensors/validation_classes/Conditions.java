package com.example.sensors.validation_classes;

import androidx.lifecycle.LiveData;

import java.util.Objects;

public class Conditions {

    public static Condition<String> TextMaxLength(final int errorMessage, final int maxLength){
        return data -> {
            if (data != null && data.length() <= maxLength){
                return  ValidationResult.valid();
            }
            return  ValidationResult.invalid(errorMessage);
        };
    }

    public static Condition<String> TextEquals(
            final LiveData<String> other,
            final Integer errorMessage){
        return data -> {
          String otherValue = other.getValue();
          if (data != null && Objects.equals(data, otherValue)) {
              return ValidationResult.valid();
          }
          return ValidationResult.invalid(errorMessage);
        };
    }
}
