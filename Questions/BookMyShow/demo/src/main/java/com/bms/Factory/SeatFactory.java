package com.bms.Factory;

import com.bms.Enums.SeatType;
import com.bms.Models.Seat.*;

public class SeatFactory {

    public static Seat createSeat(String id, int row, int col, SeatType type, int price) {
        switch (type) {
            case REGULAR:
                return new RegularSeat(id, row, col, price);

            case PREMIUM:
                return new PremiumSeat(id, row, col, price);

            case RECLINER:
                return new ReclinerSeat(id, row, col, price);

            default:
                throw new IllegalArgumentException("Invalid seat type: " + type);
        }
    }
}
