package com.bms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bms.Command.BookingCommandInvoker;
import com.bms.Command.RefundBookingCommand;
import com.bms.Enums.BookingStatus;
import com.bms.Enums.PaymentStatus;
import com.bms.Enums.SeatType;
import com.bms.Factory.SeatGenerator;
import com.bms.Models.Booking;
import com.bms.Models.City;
import com.bms.Models.Movie;
import com.bms.Models.Payment;
import com.bms.Models.Screen;
import com.bms.Models.Seat.Seat;
import com.bms.Models.Show;
import com.bms.Models.Theater;
import com.bms.Models.User;
import com.bms.Services.SeatLockingService;
import com.bms.Strategy.pricing.PricingStrategy;

public class BookingCommandWorkflowTest {

    @Test
    void refundCommand_shouldMarkBookingAsRefunded() {
        User user = new User("Alice", "alice@example.com");
        List<Seat> seats = SeatGenerator.generateSeats(2, 2, SeatType.REGULAR, 200);
        Movie movie = new Movie("m1", "Inception");
        City city = new City("c1", "Delhi");
        Theater theater = new Theater("t1", "PVR", city, List.of());
        Screen screen = new Screen("s1", theater, seats);
        theater.getScreens().add(screen);
        Show show = new Show("show-1", movie, screen, LocalDateTime.now(), new PricingStrategy() {
            @Override
            public double calculatePrice(List<Seat> selectedSeats) {
                return selectedSeats.size() * 200;
            }
        });

        Booking booking = new Booking.BookingBuilder()
                .setUser(user)
                .setShow(show)
                .setSeats(seats)
                .setTotalAmount(400)
                .setPayment(new Payment(400, PaymentStatus.SUCCESS, "txn-1"))
                .build();

        booking.confirmBooking();
        BookingCommandInvoker invoker = new BookingCommandInvoker(new RefundBookingCommand(booking, new SeatLockingService()));
        invoker.execute();

        assertEquals(BookingStatus.REFUNDED, booking.getStatus());
    }
}
