package com.bms.Models;

import java.util.ArrayList;
import java.util.List;

import com.bms.Models.Seat.Seat;

public class Screen {
    private final String id;
    private final List<Seat> seats;
    private Theater theater;

    public Screen(String id, Theater theater) {
        this.id = id;
        this.seats = new ArrayList<>();
        this.theater = theater;
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public Theater getTheater(){
        return this.theater;
    }

    public String getId() { return id; }
    public List<Seat> getSeats() { return seats; }
}
