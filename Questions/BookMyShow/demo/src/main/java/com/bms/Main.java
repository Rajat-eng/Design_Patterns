package com.bms;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.bms.Enums.SeatType;
import com.bms.Factory.SeatGenerator;
import com.bms.Models.Booking;
import com.bms.Models.City;
import com.bms.Models.Movie;
import com.bms.Models.Screen;
import com.bms.Models.Seat.Seat;
import com.bms.Models.Show;
import com.bms.Models.Theater;
import com.bms.Models.User;
import com.bms.Strategy.payment.CreditCardPaymentStrategy;
import com.bms.Strategy.pricing.WeekdayPricingStrategy;

public class Main {
    public static void main(String[] args) {
        MovieBookingService bookingService = MovieBookingService.getInstance();

        City city = bookingService.addCity("c1", "Delhi");
        Theater theater = bookingService.addCinema("t1", "PVR", city.getId(), List.of());
        Movie movie = new Movie("m1", "Inception");
        Show show = bookingService.addShow("s1", movie, new Screen("screen-1", theater), LocalDateTime.now().plusHours(2), new WeekdayPricingStrategy());

        List<Seat> seats = SeatGenerator.generateSeats(2, 2, SeatType.REGULAR, 200);
        bookingService.addSeatsToShow(show, seats);

        User user = bookingService.createUser("Alice", "alice@example.com");

        Optional<Booking> bookingOpt = bookingService.bookTickets(
                user.getId(),
                show.getId(),
                List.of(seats.get(0), seats.get(1)),
                new CreditCardPaymentStrategy("1234567890123456", "123")
        );

        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            System.out.println("Booked successfully: " + booking.getStatus());

            Optional<Booking> cancelResult = bookingService.cancelBooking(user.getId(), booking);
            System.out.println("Cancel result present: " + cancelResult.isPresent() + ", status=" + cancelResult.map(Booking::getStatus).orElse(null));

            Optional<Booking> refundResult = bookingService.refundBooking(user.getId(), booking);
            System.out.println("Refund result present: " + refundResult.isPresent() + ", status=" + refundResult.map(Booking::getStatus).orElse(null));
        }

        bookingService.shutdown();
    }
}