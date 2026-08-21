package com.amazon.ChainOfResponsibility;

public class OrderOwnershipReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected ReviewValidationResult doValidate(ReviewValidationContext context) {
        if (!context.getOrder().getCustomerId().equals(context.getCustomerId())) {
            return ReviewValidationResult.fail("Review failed: only the order owner can review this order.");
        }
        return ReviewValidationResult.ok();
    }
}
