package com.amazon.ChainOfResponsibility;

public class OrderExistsReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected boolean doValidate(ReviewValidationContext context) {
        if (context.getOrder() == null) {
            System.out.println("Review failed: order not found.");
            return false;
        }
        return true;
    }
}
