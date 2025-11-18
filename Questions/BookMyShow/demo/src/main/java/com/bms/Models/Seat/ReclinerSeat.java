package com.bms.Models.Seat;
import com.bms.Enums.SeatType;

public class ReclinerSeat extends Seat{
    public ReclinerSeat(String id, int row, int col,int price){
        super(id,row,col,SeatType.RECLINER,price);
    }
}
