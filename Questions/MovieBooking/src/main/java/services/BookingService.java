package services;

import java.util.ArrayList;
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
    private Map<String, List<Booking>> bookings;

    public BookingService(ShowService showService) {
        this.showService = showService;
        this.taxService = new TaxService();
        this.bookings = new HashMap<>();
    }

    public double bookSeatsAndGetPrice(User user, int showId, List<String> seatIds) {
        if(!showService.areSeatsAvailable(showId, seatIds)){
            System.out.println("Seats are not available for booking.");
            return 0;
        }
        int totalTicketPrice = showService.bookSeats(showId, seatIds);
        List<ISeat> seats = seatIds.stream()
                .map(id -> showService.getShow(showId).getSeatBySeatId(id))
                .collect(Collectors.toList());
        
        Booking booking = new Booking(user, showService.getShow(showId).getTheater(), showService.getShow(showId),
                seats, totalTicketPrice, BookingStatus.CONFIRMED);
        booking.setTax(taxService.generateTax(totalTicketPrice));
        bookings.computeIfAbsent(user.getEmail(), k -> new ArrayList<>()).add(booking);
        System.out.println("Successfully Booked Show - " + showId);
        System.out.println("Total ticket price: " + totalTicketPrice);
        double taxedAmount = taxService.calculateWithTaxes(totalTicketPrice);
        System.out.println("Total amount after taxes: " + taxedAmount);
        return taxedAmount;
    }

    public List<Booking> getAllBookingsByTheaterId(int theaterId) {
        return bookings.values().stream() // Stream<List<Booking>>
                // Flatten the list of bookings into a single stream
                .flatMap(list -> list.stream())
                .filter(booking -> booking.getTheater().getId() == theaterId)
                .filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

}
