package com.bms.Factory;
import java.util.ArrayList;
import java.util.List;

import com.bms.Enums.SeatType;
import com.bms.Models.Seat.Seat;

public class SeatGenerator {

    public static List<Seat> generateSeats(int rows, int cols, SeatType type, int basePrice) {
        List<Seat> seats = new ArrayList<>();

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {

                String seatId = type.toString().charAt(0) + "-" + r + "-" + c;

                Seat seat = SeatFactory.createSeat(seatId, r, c, type, basePrice);

                seats.add(seat);
            }
        }

        return seats;
    }
}
