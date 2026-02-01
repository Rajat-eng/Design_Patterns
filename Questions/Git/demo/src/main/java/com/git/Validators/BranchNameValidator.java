package com.git.Validators;

/**
 * SRP: Validates branch name format
 */
public class BranchNameValidator implements IValidator<String> {
    
    private static final String VALID_PATTERN = "^[a-zA-Z0-9/_-]+$";
    
    @Override
    public ValidationResult validate(String branchName) {
        if (branchName == null || branchName.trim().isEmpty()) {
            return ValidationResult.failure("Branch name cannot be empty");
        }
        
        String trimmed = branchName.trim();
        
        if (trimmed.startsWith("-") || trimmed.startsWith("/")) {
            return ValidationResult.failure("Branch name cannot start with '-' or '/'");
        }
        
        if (trimmed.endsWith("/")) {
            return ValidationResult.failure("Branch name cannot end with '/'");
        }
        
        if (trimmed.contains("//")) {
            return ValidationResult.failure("Branch name cannot contain consecutive slashes");
        }
        
        if (!trimmed.matches(VALID_PATTERN)) {
            return ValidationResult.failure("Branch name contains invalid characters");
        }
        
        if (trimmed.equalsIgnoreCase("HEAD")) {
            return ValidationResult.failure("Branch name cannot be 'HEAD'");
        }
        
        return ValidationResult.success();
    }
}
