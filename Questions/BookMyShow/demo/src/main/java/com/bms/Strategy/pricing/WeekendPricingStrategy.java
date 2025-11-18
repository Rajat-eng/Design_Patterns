package com.bms.Strategy.pricing;

import java.util.List;

import com.bms.Models.Seat.Seat;

public class WeekendPricingStrategy implements PricingStrategy {
    private static final double WEEKEND_SURCHARGE = 1.2; // 20% surcharge

    @Override
    public double calculatePrice(List<Seat> seats) {
        double basePrice = seats.stream().mapToDouble(seat -> seat.getPrice()).sum();
        return basePrice * WEEKEND_SURCHARGE;
    }
}
