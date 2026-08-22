package com.amazon.ChainOfResponsibility;

public class DuplicateReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected boolean doValidate(ReviewValidationContext context) {
        if (context.getExistingReviews().containsKey(context.getReviewKey())) {
            System.out.println("Review failed: review already submitted for this product in this order.");
            return false;
        }
        return true;
    }
}
