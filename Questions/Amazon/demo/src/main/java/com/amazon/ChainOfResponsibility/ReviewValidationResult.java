package com.amazon.ChainOfResponsibility;

public class ReviewValidationResult {
    private final boolean valid;
    private final String message;

    private ReviewValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public static ReviewValidationResult ok() {
        return new ReviewValidationResult(true, "OK");
    }

    public static ReviewValidationResult fail(String message) {
        return new ReviewValidationResult(false, message);
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }
}
