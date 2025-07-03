package com.example.sensors.validation_classes;

public interface Condition<T>{
    ValidationResult validate(T data);
}
