package com.bms;

import java.util.List;

import com.bms.Enums.SeatType;
import com.bms.Factory.SeatGenerator;
import com.bms.Models.Seat.Seat;

public class Main {
        public static void main(String[] args) {
            List<Seat> regularSeats = SeatGenerator.generateSeats(
            10,      // rows
            20,      // cols
            SeatType.REGULAR,
            200      // price
        );

        List<Seat> premiumSeats = SeatGenerator.generateSeats(
                5,
                10,
                SeatType.PREMIUM,
                400
        );

        List<Seat> reclinerSeats = SeatGenerator.generateSeats(
                2,
                10,
                SeatType.RECLINER,
                400
        );
    }
}