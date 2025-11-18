package com.bms.Models.Seat;

import com.bms.Enums.SeatType;


public class PremiumSeat extends Seat {
    public PremiumSeat(String id, int row, int col, int price){
        super(id, row, col, SeatType.PREMIUM, price);
    }
}
