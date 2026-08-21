package com.amazon.ChainOfResponsibility;

import com.amazon.Enums.OrderStatus;

public class DeliveredOrderReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected ReviewValidationResult doValidate(ReviewValidationContext context) {
        if (context.getOrder().getStatus() != OrderStatus.DELIVERED) {
            return ReviewValidationResult.fail("Review failed: order must be delivered before review.");
        }
        return ReviewValidationResult.ok();
    }
}
