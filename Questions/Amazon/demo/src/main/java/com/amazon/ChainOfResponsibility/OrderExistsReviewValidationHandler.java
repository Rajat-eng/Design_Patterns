package com.amazon.ChainOfResponsibility;

public class OrderExistsReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected ReviewValidationResult doValidate(ReviewValidationContext context) {
        if (context.getOrder() == null) {
            return ReviewValidationResult.fail("Review failed: order not found.");
        }
        return ReviewValidationResult.ok();
    }
}
