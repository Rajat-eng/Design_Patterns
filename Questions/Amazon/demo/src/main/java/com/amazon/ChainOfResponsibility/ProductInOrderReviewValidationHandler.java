package com.amazon.ChainOfResponsibility;

public class ProductInOrderReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected boolean doValidate(ReviewValidationContext context) {
        boolean present = context.getOrder().getItems().stream()
                .anyMatch(item -> item.getProductId().equals(context.getProductId()));
        if (!present) {
            System.out.println("Review failed: product is not part of this order.");
            return false;
        }
        return true;
    }
}
