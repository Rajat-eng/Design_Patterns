package com.amazon.ChainOfResponsibility;

public class DuplicateReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected ReviewValidationResult doValidate(ReviewValidationContext context) {
        if (context.getExistingReviews().containsKey(context.getReviewKey())) {
            return ReviewValidationResult.fail("Review failed: review already submitted for this product in this order.");
        }
        return ReviewValidationResult.ok();
    }
}
