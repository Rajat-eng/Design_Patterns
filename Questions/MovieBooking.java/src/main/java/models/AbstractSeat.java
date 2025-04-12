package models;

import models.enums.SeatStatus;
import models.enums.SeatType;
import strategies.ISeat;

public abstract class AbstractSeat implements ISeat {
    private String seatId;
    private SeatType seatType;
    private SeatStatus seatStatus;
    private int price;

    public AbstractSeat(String seatId, SeatType seatType, int price) {
        this.seatId = seatId;
        this.seatType = seatType;
        this.price = price;
        this.seatStatus = SeatStatus.AVAILABLE;
    }

    @Override
    public String getSeatId() {
        return seatId;
    }
    @Override
    public int getPrice(){
        return this.price;
    }

    @Override
    public SeatType getSeatType(){
        return this.seatType;
    }

    @Override
    public void setSeatStatus(SeatStatus seatStatus){
        this.seatStatus=seatStatus;
    }
    @Override
    public SeatStatus getSeatStatus(){
        return this.seatStatus;
    }

    @Override
    public boolean isSeatBooked(){
        return this.seatStatus==SeatStatus.BOOKED;
    }
}
