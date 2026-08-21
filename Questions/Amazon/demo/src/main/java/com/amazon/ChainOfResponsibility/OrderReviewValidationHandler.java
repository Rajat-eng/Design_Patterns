package com.amazon.ChainOfResponsibility;

public abstract class OrderReviewValidationHandler {
    private OrderReviewValidationHandler next;

    public OrderReviewValidationHandler setNext(OrderReviewValidationHandler next) {
        this.next = next;
        return this;
    }

    public ReviewValidationResult validate(ReviewValidationContext context) {
        ReviewValidationResult currentResult = doValidate(context);
        // context is chain of responsibility, if current result is not valid, return it, else pass to next handler
        if (!currentResult.isValid()) {
            return currentResult;
        }
        if (next == null) {
            return ReviewValidationResult.ok();
        }
        return next.validate(context);  // recursively call the next handler in the chain  
    }

    protected abstract ReviewValidationResult doValidate(ReviewValidationContext context);
}
