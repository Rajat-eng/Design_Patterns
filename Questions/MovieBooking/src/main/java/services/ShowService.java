package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Show;
import models.enums.SeatStatus;
import models.enums.SeatType;
import strategies.ISeat;

public class ShowService {
    Map<Integer, Show> showMap = new HashMap<>();

    public void addShow(int showId, Show show) {
        showMap.put(showId, show);
    }

    public Show getShow(int showId) {
        return showMap.get(showId);
    }

    public boolean isValidShow(int showId) {
        return getShow(showId) != null;
    }

    public void addSeatsToShow(List<String> seatIds, int showId) {
        Show show = showMap.get(showId);
        if (!isValidShow(showId)) {
            System.out.println("Show ID " + showId + " is not valid.");
            return;
        }

        show.addSeatsToShow(seatIds);

    }

    public void addShowAndSeats(Show show, List<String> seatIds) {
        addShow(show.getShowID(), show);
        addSeatsToShow(seatIds, show.getShowID());
    }

    public void displayAllShows() {
        for (Map.Entry<Integer, Show> entry : showMap.entrySet()) {
            System.out.println("Show ID: " + entry.getKey());
            Show show = entry.getValue();
            List<ISeat> seatsForTheShow=show.getAllSeats();
            displaySeats(seatsForTheShow);
        }
    }

    public void displaySeats(List<ISeat> seats) {
        List<SeatType> seatOrder = List.of(SeatType.PLATINUM, SeatType.GOLD, SeatType.SILVER);
        Map<SeatType, List<ISeat>> groupedSeats = new LinkedHashMap<>();
        for (SeatType type : seatOrder) {
            groupedSeats.put(type, new ArrayList<>());
        }
    
        for (ISeat seat : seats) {
            groupedSeats.get(seat.getSeatType()).add(seat);
        }

        for (Map.Entry<SeatType, List<ISeat>> entry : groupedSeats.entrySet()) {
            // System.out.println(entry.getKey()); 
            for (ISeat seat : entry.getValue()) {
                if(seat.isSeatBooked()){
                    continue;
                }
                System.out.print(seat.getSeatId() + " ");
            }
            System.out.println("\n");
        }
    
        System.out.println("--------------------SCREEN----------------------------");
    }

    public boolean areSeatsAvailable(int showId, List<String> seats) {
        if (!this.isValidShow(showId)) {
            System.out.println("Show ID " + showId + " is not valid.");
            return false;
        }
        for (String seat : seats) {
            ISeat requiredSeat = this.getShow(showId).getSeatBySeatId(seat);
            if (requiredSeat == null) {
                System.out.println("Seat " + seat + " is not valid.");
                return false;
            }
            if (requiredSeat.isSeatBooked()) {
                System.out.println("Seat " + seat + " is not available.");
                return false;
            }
        }
        return true;
    }

    public int bookSeats(int showId, List<String> seats) {
        if(!areSeatsAvailable(showId, seats) || !isValidShow(showId)){
            return 0;
        }
        int totalBookedAmount = 0;
        for (String seat : seats) {
            ISeat requiredSeat = this.getShow(showId).getSeatBySeatId(seat);
            requiredSeat.setSeatStatus(SeatStatus.BOOKED);
            totalBookedAmount += requiredSeat.getPrice();
        }
        return totalBookedAmount;
    }

    public List<Show> getAllShows() {
        return showMap.values().stream().toList();
    }
}
