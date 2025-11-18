package com.bms.Models.Seat;
import com.bms.Enums.SeatType;

public class RegularSeat extends Seat {
    public RegularSeat(String id, int row, int col,int price){
        super(id,row,col,SeatType.REGULAR,price);
    }
}
