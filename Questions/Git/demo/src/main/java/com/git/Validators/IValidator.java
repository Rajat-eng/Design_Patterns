package com.git.Validators;

/**
 * Strategy Pattern + SRP:
 * Base interface for all validators
 */
public interface IValidator<T> {
    ValidationResult validate(T data);
}
