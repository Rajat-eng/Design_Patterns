package com.amazon.Models;

import java.time.LocalDateTime;

public class OrderReview {
    private final String orderId;
    private final String productId;
    private final String customerId;
    private final int rating;
    private final String comment;
    private final LocalDateTime reviewedAt;

    public OrderReview(String orderId, String productId, String customerId, int rating, String comment) {
        this.orderId = orderId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
        this.reviewedAt = LocalDateTime.now();
    }

    public String getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public String getCustomerId() { return customerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
}