package com.example.sensors.validation_classes;

import java.util.Arrays;
import java.util.List;

public class Validator<T> {
    private final List<Condition<T>> conditions;

    public Validator(Condition<T>... conditions){
        this.conditions = Arrays.asList(conditions);
    }

    public ValidationResult validate(T data){
        for (Condition<T> condition : conditions){
            ValidationResult result = condition.validate(data);
            if (!result.isValid()){
                return result;
            }
        }
        return ValidationResult.valid();
    }
}
