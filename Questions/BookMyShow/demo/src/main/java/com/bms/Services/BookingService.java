package com.bms.Services;

import java.util.List;
import java.util.Optional;

import com.bms.Decorator.BasePrice;
import com.bms.Decorator.GstDecorator;
import com.bms.Decorator.ServiceDecorator;
import com.bms.Enums.PaymentStatus;
import com.bms.Models.Booking;
import com.bms.Models.Payment;
import com.bms.Models.Seat.Seat;
import com.bms.Models.Show;
import com.bms.Models.User;
import com.bms.Strategy.payment.PaymentStrategy;

public class BookingService {
     private final SeatLockingService seatLockService;

    public BookingService(SeatLockingService seatLockService) {
        this.seatLockService = seatLockService;
    }

    public Optional<Booking> createBooking(User user, Show show, List<Seat> seats , PaymentStrategy paymentStrategy){

        boolean locked = seatLockService.lockSeats(show, seats, user.getId());
        if (!locked) {
            System.out.println("Seats cannot be locked. Booking cancelled.");
            return Optional.empty();
        }

        double totalAmount = show.getPricingStrategy().calculatePrice(seats);

        double taxedAmount =
                new ServiceDecorator(
                    new GstDecorator(
                        new BasePrice(totalAmount)
                    )
                ).getAmount();

        Payment payment = paymentStrategy.pay(taxedAmount);

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            Booking booking = new Booking.BookingBuilder()
                    .setUser(user)
                    .setShow(show)
                    .setSeats(seats)
                    .setTotalAmount(taxedAmount)
                    .setPayment(payment)
                    .build();

            booking.confirmBooking();

            seatLockService.unlockSeats(show, seats, user.getId());

            return Optional.of(booking);
        }

        // If payment fails, release lock
        seatLockService.unlockSeats(show, seats, user.getId());
        return Optional.empty();
    }

}
