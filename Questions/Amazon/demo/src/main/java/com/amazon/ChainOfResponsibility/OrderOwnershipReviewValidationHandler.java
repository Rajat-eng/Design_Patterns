package com.amazon.ChainOfResponsibility;

public class OrderOwnershipReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected boolean doValidate(ReviewValidationContext context) {
        if (!context.getOrder().getCustomerId().equals(context.getCustomerId())) {
            System.out.println("Review failed: only the order owner can review this order.");
            return false;
        }
        return true;
    }
}
