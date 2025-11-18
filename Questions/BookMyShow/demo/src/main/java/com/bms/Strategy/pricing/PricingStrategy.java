package com.bms.Strategy.pricing;

import java.util.List;

import com.bms.Models.Seat.Seat;

public interface PricingStrategy {
    double calculatePrice(List<Seat> seats);
}
