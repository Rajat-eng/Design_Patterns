package com.git.Validators;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite Pattern:
 * Combines multiple validators
 */
public class CompositeValidator<T> implements IValidator<T> {
    
    private final List<IValidator<T>> validators;
    
    public CompositeValidator() {
        this.validators = new ArrayList<>();
    }
    
    public void addValidator(IValidator<T> validator) {
        validators.add(validator);
    }
    
    @Override
    public ValidationResult validate(T data) {
        for (IValidator<T> validator : validators) {
            ValidationResult result = validator.validate(data);
            if (!result.isValid()) {
                return result; // Return first failure
            }
        }
        return ValidationResult.success();
    }
}
