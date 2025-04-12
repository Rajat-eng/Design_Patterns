package models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.enums.SeatType;
import strategies.ISeat;
import strategies.SeatFactory;

public class Show {
    private int showId;
    private Map<String, ISeat> seats;
    private Movie movie;
    private Theater theater;
    private String showTime;
    private String showDate;

    public Show(int showNumber) {
        this.showId = showNumber;
        this.seats = new LinkedHashMap<>();
    }

    public void setMovieAndTheaterForTheShow(Movie movie, Theater theater) {
        this.movie = movie;
        this.theater = theater;
    }

    public void addSeatsToShow(List<String> seatIds) {
        for (String seatId : seatIds) {
            ISeat seat = SeatFactory.createSeat(seatId);
            this.seats.put(seatId, seat);
        }
    }

    public int getShowID() {
        return this.showId;
    }

    public ISeat getSeatBySeatId(String seatId) {
        return this.seats.get(seatId);
    }

    public List<ISeat> getAllSeats() {
        return List.copyOf(this.seats.values());
    }

    public void displaySeats() {
       
        List<SeatType> seatOrder = List.of(SeatType.PLATINUM, SeatType.GOLD, SeatType.SILVER);
    
      
        Map<SeatType, List<ISeat>> groupedSeats = new LinkedHashMap<>();
        for (SeatType type : seatOrder) {
            groupedSeats.put(type, new ArrayList<>());
        }
    
        for (ISeat seat : this.seats.values()) {
            groupedSeats.get(seat.getSeatType()).add(seat);
        }

        for (Map.Entry<SeatType, List<ISeat>> entry : groupedSeats.entrySet()) {
            System.out.println(entry.getKey()); 
            for (ISeat seat : entry.getValue()) {
                System.out.print(seat.getSeatId() + " ");
            }
            System.out.println("\n");
        }
    
        System.out.println("--------------------SCREEN----------------------------");
    }
    
}
