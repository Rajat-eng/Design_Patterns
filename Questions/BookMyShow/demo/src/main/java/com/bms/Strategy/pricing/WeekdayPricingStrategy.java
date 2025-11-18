package com.bms.Strategy.pricing;

import java.util.List;

import com.bms.Models.Seat.Seat;

public class WeekdayPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(List<Seat>seats){
         return seats.stream().mapToDouble(seat -> seat.getPrice()).sum();
    }
}
