package models;

import models.enums.SeatType;

public class PlatinumSeat extends AbstractSeat{
    public PlatinumSeat(String seatId, int price) {
        super(seatId, SeatType.PLATINUM, price);
    }
}
