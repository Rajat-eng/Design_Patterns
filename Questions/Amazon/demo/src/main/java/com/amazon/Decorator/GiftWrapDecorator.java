package com.amazon.Decorator;

import com.amazon.Models.Product;

public class GiftWrapDecorator extends ProductDecorator {
    private static final double GIFT_WRAP_COST = 5.00;

    public GiftWrapDecorator(Product product) {
        super(product);
    }

    @Override
    public double getPrice() {
        return super.getPrice() + GIFT_WRAP_COST;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (Gift Wrapped)";
    }
}
