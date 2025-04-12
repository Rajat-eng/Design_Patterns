package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.Booking;
import models.User;
import models.enums.BookingStatus;
import strategies.ISeat;

public class BookingService {
    private ShowService showService;
    private TaxService taxService;
    private User user;
    Map<String,Booking> bookings;

    public BookingService(ShowService showService) {
        this.showService = showService;
        this.taxService = new TaxService();
        this.bookings= new HashMap<>();
    }   
   
    public double bookSeatsAndGetPrice(User user,int showId, List<String> seatIds) {
        int totalTicketPrice=showService.bookSeats(showId,seatIds);
        List<ISeat> seats =  seatIds.stream()
                            .map(id -> showService.getShow(showId).getSeatBySeatId(id))
                            .collect(Collectors.toList());
        Booking booking = new Booking(1,user,showService.getShow(showId),seats,totalTicketPrice,BookingStatus.CONFIRMED);
        bookings.put(user.getEmail(),booking);
        System.out.println("Seats Booked Successfully.");
        System.out.println("Total ticket price: " + totalTicketPrice);
        double taxedAmount = taxService.calculateTotalWithTaxes(totalTicketPrice);
        return taxedAmount;
    }
    
}
