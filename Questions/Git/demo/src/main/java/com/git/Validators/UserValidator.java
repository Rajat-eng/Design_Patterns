package com.git.Validators;

import com.git.Models.User.User;

/**
 * SRP: Validates user data
 */
public class UserValidator implements IValidator<User> {
    
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    
    @Override
    public ValidationResult validate(User user) {
        if (user == null) {
            return ValidationResult.failure("User cannot be null");
        }
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return ValidationResult.failure("User name cannot be empty");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return ValidationResult.failure("User email cannot be empty");
        }
        
        if (!user.getEmail().matches(EMAIL_PATTERN)) {
            return ValidationResult.failure("Invalid email format");
        }
        
        return ValidationResult.success();
    }
}
