package com.amazon.ChainOfResponsibility;

public class RatingRangeReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected boolean doValidate(ReviewValidationContext context) {
        if (context.getRating() < 1 || context.getRating() > 5) {
            System.out.println("Review failed: rating must be between 1 and 5.");
            return false;
        }
        return true;
    }
}
