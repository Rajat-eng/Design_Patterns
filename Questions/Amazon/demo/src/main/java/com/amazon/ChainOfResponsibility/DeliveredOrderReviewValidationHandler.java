package com.amazon.ChainOfResponsibility;

import com.amazon.Enums.OrderStatus;

public class DeliveredOrderReviewValidationHandler extends OrderReviewValidationHandler {
    @Override
    protected boolean doValidate(ReviewValidationContext context) {
        if (context.getOrder().getStatus() != OrderStatus.DELIVERED) {
            System.out.println("Review failed: order must be delivered before review.");
            return false;
        }
        return true;
    }
}
