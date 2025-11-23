package com.bms.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bms.Enums.SeatType;
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

    public void displaySeats() {

    // 1. Group seats into map
    Map<SeatType, List<Seat>> seatMap = new HashMap<>();

    for (Seat seat : seats) {
        seatMap
            .computeIfAbsent(seat.getType(), k -> new ArrayList<>())
            .add(seat);
    }

    // 2. Print based on type
    System.out.println("Seat Layout for Screen: " + id + "\n");

    for (SeatType type : seatMap.keySet()) {

        List<Seat> typeSeats = seatMap.get(type);

        if (typeSeats == null || typeSeats.isEmpty()) continue;

        // 3. Sort seats by row then col
        typeSeats.sort((a, b) -> {
            if (a.getRow() != b.getRow())
                return a.getRow() - b.getRow();
            return a.getCol() - b.getCol();
        });

        System.out.println("=== " + type + " SEATS ===");

        int currentRow = -1;

        // 4. Print seats row by row (NO 2D array)
        for (Seat s : typeSeats) {

            if (s.getRow() != currentRow) {
                currentRow = s.getRow();
                System.out.println(); // new row
                System.out.print("Row " + currentRow + ": ");
            }

            // Show seat type initial
            System.out.print(type.toString().charAt(0) + "(" + s.getCol() + ")  ");
        }

        System.out.println("\n");
    }
}

}
