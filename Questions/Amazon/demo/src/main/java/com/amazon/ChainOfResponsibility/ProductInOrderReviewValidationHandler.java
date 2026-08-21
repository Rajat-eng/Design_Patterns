package com.amazon.ChainOfResponsibility;

public class ProductInOrderReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected ReviewValidationResult doValidate(ReviewValidationContext context) {
        boolean present = context.getOrder().getItems().stream()
                .anyMatch(item -> item.getProductId().equals(context.getProductId()));
        if (!present) {
            return ReviewValidationResult.fail("Review failed: product is not part of this order.");
        }
        return ReviewValidationResult.ok();
    }
}
