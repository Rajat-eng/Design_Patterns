package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.enums.SeatStatus;
import strategies.ISeat;
import strategies.SeatFactory;

public class Show {
    private int showId;
    private Map<String, ISeat> seats;
    private Movie movie;
    private Theater theater;

    public Show(int showNumber) {
        this.showId = showNumber;
        this.seats = new LinkedHashMap<>();
    }

    public void setMovieAndTheaterForTheShow(Movie movie, Theater theater) {
        this.movie = movie;
        this.theater = theater;
        this.theater.addShow(this);
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

    public Movie getMovie() {
        return movie;
    }
    public Theater getTheater() {
        return this.theater;
    }

    public double getTotalRevenueForShow(){
        double totalRevenue=0;
        totalRevenue+=this.getAllSeats().stream()
                .filter(seat->seat.getSeatStatus()==SeatStatus.BOOKED)
                .mapToDouble(seat->seat.getPrice()).sum();
        
        return totalRevenue;
    } 
    
}
