package com.amazon.ChainOfResponsibility;

import java.util.Map;

import com.amazon.Models.Order;
import com.amazon.Models.OrderReview;

public class ReviewValidationContext {
    private final String customerId;
    private final String orderId;
    private final String productId;
    private final int rating;
    private final Order order;
    private final Map<String, OrderReview> existingReviews;
    private final String reviewKey;

    public ReviewValidationContext(
            String customerId,
            String orderId,
            String productId,
            int rating,
            Order order,
            Map<String, OrderReview> existingReviews,
            String reviewKey
    ) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.productId = productId;
        this.rating = rating;
        this.order = order;
        this.existingReviews = existingReviews;
        this.reviewKey = reviewKey;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public int getRating() {
        return rating;
    }

    public Order getOrder() {
        return order;
    }

    public Map<String, OrderReview> getExistingReviews() {
        return existingReviews;
    }

    public String getReviewKey() {
        return reviewKey;
    }
}
