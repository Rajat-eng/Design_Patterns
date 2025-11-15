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


    public static class BaseProduct extends Product {
        public BaseProduct(String id, String name, String description, double price, ProductCategory category) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.category = category;
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public double getPrice() {
            return this.price;
        }

        @Override
        public ProductCategory getCategory() {
            return this.category;
        }
    }

    public static class Builder {
        private Product product;

        public Builder(String name, double price) {
            this.product.name = name;
            this.product.price = price;
        }
        public Builder withDescription(String description) { this.product.description = description; return this; }
        public Builder withCategory(ProductCategory category) { this.product.category = category; return this; }
        public Product build() {
            return new BaseProduct(UUID.randomUUID().toString(), this.product.name, this.product.description, this.product.price, this.product.category);
        }
    }
}
