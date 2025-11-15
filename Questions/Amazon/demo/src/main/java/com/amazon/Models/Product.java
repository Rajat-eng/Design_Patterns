package com.amazon.Models;

import java.util.UUID;

public abstract class Product {
    protected String id;
    protected String name;
    protected String description;
    protected double price;
    protected ProductCategory category;

    public abstract String getId();
    public abstract String getName();
    public abstract String getDescription();
    public abstract double getPrice();
    public abstract ProductCategory getCategory();

    // ---------------- Concrete Product ----------------
    public static class BaseProduct extends Product {

        public BaseProduct(String id, String name, String description, double price, ProductCategory category) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.category = category;
        }

        @Override public String getId() { return id; }
        @Override public String getName() { return name; }
        @Override public String getDescription() { return description; }
        @Override public double getPrice() { return price; }
        @Override public ProductCategory getCategory() { return category; }
    }

    // ---------------- Builder ----------------
    public static class Builder {
        private String name;
        private String description;
        private double price;
        private ProductCategory category;

        public Builder(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCategory(ProductCategory category) {
            this.category = category;
            return this;
        }

        public Product build() {
            return new BaseProduct(
                    UUID.randomUUID().toString(),
                    name,
                    description,
                    price,
                    category
            );
        }
    }
}
