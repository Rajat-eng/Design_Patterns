package com.git.Validators;

import com.git.Models.User.User;

/**
 * SRP: Validates commit message format
 */
public class CommitMessageValidator implements IValidator<String> {
    
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 100;
    
    @Override
    public ValidationResult validate(String message) {
        if (message == null || message.trim().isEmpty()) {
            return ValidationResult.failure("Commit message cannot be empty");
        }
        
        String trimmed = message.trim();
        
        if (trimmed.length() < MIN_LENGTH) {
            return ValidationResult.failure("Commit message too short (min: " + MIN_LENGTH + " chars)");
        }
        
        if (trimmed.length() > MAX_LENGTH) {
            return ValidationResult.failure("Commit message too long (max: " + MAX_LENGTH + " chars)");
        }
        
        return ValidationResult.success();
    }
}
