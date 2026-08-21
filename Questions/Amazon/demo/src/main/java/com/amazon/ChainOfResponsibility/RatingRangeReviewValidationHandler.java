package com.amazon.ChainOfResponsibility;

public class RatingRangeReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected ReviewValidationResult doValidate(ReviewValidationContext context) {
        if (context.getRating() < 1 || context.getRating() > 5) {
            return ReviewValidationResult.fail("Review failed: rating must be between 1 and 5.");
        }
        return ReviewValidationResult.ok();
    }
}
