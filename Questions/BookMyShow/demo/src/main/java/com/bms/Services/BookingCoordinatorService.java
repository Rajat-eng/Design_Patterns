package com.bms.Services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.bms.Command.BookingCommandInvoker;
import com.bms.Command.CancelBookingCommand;
import com.bms.Command.ConfirmBookingCommand;
import com.bms.Command.RefundBookingCommand;
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

public class BookingCoordinatorService {
    private final SeatLockingService seatLockingService;
    private final BookingIdempotencyService idempotencyService;
    private final PaymentService paymentService;

    public BookingCoordinatorService(SeatLockingService seatLockingService) {
        this(seatLockingService, new BookingIdempotencyService(), null);
    }

    public BookingCoordinatorService(SeatLockingService seatLockingService, BookingIdempotencyService idempotencyService) {
        this(seatLockingService, idempotencyService, null);
    }

    public BookingCoordinatorService(SeatLockingService seatLockingService, BookingIdempotencyService idempotencyService, PaymentService paymentService) {
        this.seatLockingService = seatLockingService;
        this.idempotencyService = idempotencyService;
        this.paymentService = paymentService != null ? paymentService : new PaymentService(new com.bms.Strategy.payment.CreditCardPaymentStrategy("0000", "000"));
    }

    public Optional<Booking> createBooking(User user, Show show, List<Seat> seats, PaymentStrategy paymentStrategy) {
        if (user == null || show == null || seats == null || seats.isEmpty()) {
            System.out.println("Booking request is invalid.");
            return Optional.empty();
        }

        if (idempotencyService.isDuplicate(user, show, seats)) {
            System.out.println("Duplicate booking request detected. Ignoring request for user " + user.getId());
            return Optional.empty();
        }

        boolean locked = seatLockingService.lockSeats(show, seats, user.getId(), 2, TimeUnit.SECONDS);
        if (!locked) {
            System.out.println("Booking could not acquire show seat lock. Try again later.");
            return Optional.empty();
        }

        Payment payment = null;
        try {
            double subtotal = show.getPricingStrategy().calculatePrice(seats);
            double total = new ServiceDecorator(new GstDecorator(new BasePrice(subtotal))).getAmount();
            payment = paymentService.charge(total);

            if (payment.getStatus() != PaymentStatus.SUCCESS) {
                seatLockingService.releaseSeats(show, seats, user.getId());
                return Optional.empty();
            }

            try {
                Booking booking = new Booking.BookingBuilder()
                        .setUser(user)
                        .setShow(show)
                        .setSeats(seats)
                        .setTotalAmount(total)
                        .setPayment(payment)
                        .build();

                new BookingCommandInvoker(new ConfirmBookingCommand(booking)).execute();
                seatLockingService.confirmSeats(show, seats, user.getId());
                idempotencyService.markCompleted(user, show, seats);
                return Optional.of(booking);
            } catch (Exception bookingException) {
                seatLockingService.releaseSeats(show, seats, user.getId());
                paymentService.refund(payment);
                System.out.println("Payment succeeded but booking failed; refunding payment for user " + user.getId());
                return Optional.empty();
            }
        } catch (Exception e) {
            seatLockingService.releaseSeats(show, seats, user.getId());
            if (payment != null && payment.getStatus() == PaymentStatus.SUCCESS) {
                paymentService.refund(payment);
            }
            System.out.println("Booking failed and seats were released: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Booking> cancelBooking(User user, Booking booking) {
        if (booking == null || user == null) {
            System.out.println("Cancellation request is invalid.");
            return Optional.empty();
        }

        if (!booking.getUser().getId().equals(user.getId())) {
            System.out.println("User is not allowed to cancel this booking.");
            return Optional.empty();
        }

        new BookingCommandInvoker(new CancelBookingCommand(booking, seatLockingService)).execute();
        return Optional.of(booking);
    }

    public Optional<Booking> refundBooking(User user, Booking booking) {
        if (booking == null || user == null) {
            System.out.println("Refund request is invalid.");
            return Optional.empty();
        }

        if (!booking.getUser().getId().equals(user.getId())) {
            System.out.println("User is not allowed to refund this booking.");
            return Optional.empty();
        }

        new BookingCommandInvoker(new RefundBookingCommand(booking, seatLockingService)).execute();
        return Optional.of(booking);
    }
}
