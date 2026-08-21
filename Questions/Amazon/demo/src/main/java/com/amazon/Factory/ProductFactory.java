package com.amazon.Factory;

import com.amazon.Models.Product;
import com.amazon.Models.ProductCategory;

public class ProductFactory {
    public Product createProduct(String name, double price, String description, ProductCategory category) {
        return new Product.Builder(name, price)
                .withDescription(description)
                .withCategory(category)
                .build();
    }
}
