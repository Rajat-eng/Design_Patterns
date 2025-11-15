package models;

import models.enums.SeatType;

public class GoldSeat extends AbstractSeat {
    public GoldSeat(String seatId, int price) {
        super(seatId, SeatType.GOLD, price);
    }
}
