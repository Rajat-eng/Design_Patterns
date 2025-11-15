package models;

import models.enums.SeatType;

public class SilverSeat extends AbstractSeat {
    public SilverSeat(String seatId, int price) {
        super(seatId, SeatType.SILVER, price);
    }
}
