package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Show;
import models.enums.SeatStatus;
import strategies.ISeat;

public class ShowService {
    Map<Integer, Show> showMap = new HashMap<>();

    public void addShow(int showId, Show show) {
        showMap.put(showId, show);
        System.out.println("Show added successfully.");
    }

    public Show getShow(int showId) {
        return showMap.get(showId);
    }

    public boolean isValidShow(int showId){
        return getShow(showId)!=null;
    }

    public void addSeatsToShow(List<String> seatIds, int showId){
        Show show = showMap.get(showId);
        if (show != null) {
            show.addSeatsToShow(seatIds);
            System.out.println("Seats added successfully for show ID: " + showId);
        } else {
            System.out.println("Show not found.");
        }
    }

    public void addShowAndSeats(int showId, List<String> seatIds) {
        Show show = new Show(showId);
        addShow(showId, show);
        addSeatsToShow(seatIds, showId);
    }

    public void displayAllShows(){ 
        for(Map.Entry<Integer, Show> entry: showMap.entrySet()){
            System.out.println("Show ID: " + entry.getKey());
            Show show = entry.getValue();
            show.displaySeats();
        }
    }

    public boolean areSeatsAvailable(int showId,List<String> seats){
        Show currentShow = this.getShow(showId);
        System.out.println("current show " + currentShow.getShowID());
        for(String seat:seats){
            ISeat requiredSeat = this.getShow(showId).getSeatBySeatId(seat);
            if(requiredSeat==null){
                System.out.println("Seat " + seat + " is not valid.");
                return false;
            }
            System.out.println(requiredSeat.getSeatStatus());
            if(requiredSeat.isSeatBooked()){
                System.out.println("Seat " + seat + " is not available.");
                return false;
            }
        }
        return true;
    }

    public int bookSeats(int showId,List<String> seats){
        int totalBookedAmount=0;
        for(String seat:seats){
            ISeat requiredSeat = this.getShow(showId).getSeatBySeatId(seat);
            requiredSeat.setSeatStatus(SeatStatus.BOOKED);
            totalBookedAmount+=requiredSeat.getPrice();
        }
        return totalBookedAmount;
    }

    public List<Show> getAllShows(){
        return showMap.values().stream().toList();
    }

}
