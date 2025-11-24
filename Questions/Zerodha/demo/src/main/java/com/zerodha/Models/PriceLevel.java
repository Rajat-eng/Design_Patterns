package com.zerodha.Models;

import java.util.ArrayDeque;
import java.util.Deque;

import com.zerodha.Models.Order.Order;

public class PriceLevel {
    final double price;
        final Deque<Order> queue = new ArrayDeque<>();
        PriceLevel(double price) { this.price = price; }
}
