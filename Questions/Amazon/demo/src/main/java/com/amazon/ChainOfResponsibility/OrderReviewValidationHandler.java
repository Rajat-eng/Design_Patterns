package com.amazon.ChainOfResponsibility;

public abstract class OrderReviewValidationHandler {
    private OrderReviewValidationHandler next;

    public OrderReviewValidationHandler setNext(OrderReviewValidationHandler next) {
        this.next = next;
        return this;
    }

    public boolean validate(ReviewValidationContext context) {
        boolean currentResult = doValidate(context);
        // context is chain of responsibility, if current result is not valid, return it, else pass to next handler
        if (!currentResult) {
            return false;
        }
        if (next == null) {
            return true;
        }
        return next.validate(context);  // recursively call the next handler in the chain  
    }

    protected abstract boolean doValidate(ReviewValidationContext context);
}
