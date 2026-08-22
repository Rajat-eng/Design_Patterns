package com.bms.Models;

import java.util.List;

import com.bms.Enums.BookingStatus;
import com.bms.Enums.SeatStatus;
import com.bms.Models.Seat.Seat;

public class Booking {
    private final String id;
    private final User user;
    private final Show show;
    private final List<Seat> seats;
    private final double totalAmount;
    private final Payment payment;
    private BookingStatus status;

    // Private constructor to be used by the Builder
    private Booking(String id, User user, Show show, List<Seat> seats, double totalAmount, Payment payment) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.totalAmount = totalAmount;
        this.payment = payment;
        this.status = BookingStatus.LOCKED;
    }

    public void confirmBooking() {
        if (this.status == BookingStatus.BOOKED) {
            return;
        }
        if (this.status != BookingStatus.LOCKED) {
            System.out.println("Booking cannot be confirmed from status: " + this.status);
            return;
        }
        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.BOOKED);
        }
        this.status = BookingStatus.BOOKED;
        System.out.println("Booking confirmed: " + id);
    }

    public void cancelBooking() {
        if (this.status == BookingStatus.CANCELLED || this.status == BookingStatus.REFUNDED) {
            return;
        }
        if (this.status == BookingStatus.BOOKED) {
            System.out.println("Booking already confirmed and cannot be cancelled directly.");
            return;
        }
        for (Seat seat : seats) {
            if (seat.getStatus() == SeatStatus.BOOKED || seat.getStatus() == SeatStatus.LOCKED) {
                seat.setStatus(SeatStatus.AVAILABLE);
            }
        }
        this.status = BookingStatus.CANCELLED;
        System.out.println("Booking cancelled: " + id);
    }

    public void expireBooking() {
        if (this.status == BookingStatus.EXPIRED || this.status == BookingStatus.BOOKED) {
            return;
        }
        for (Seat seat : seats) {
            if (seat.getStatus() == SeatStatus.LOCKED) {
                seat.setStatus(SeatStatus.AVAILABLE);
            }
        }
        this.status = BookingStatus.EXPIRED;
        System.out.println("Booking expired: " + id);
    }

    public void refundBooking() {
        if (this.status == BookingStatus.REFUNDED) {
            return;
        }
        if (this.status != BookingStatus.BOOKED && this.status != BookingStatus.CANCELLED) {
            System.out.println("Only booked or cancelled booking can be refunded. Current status: " + this.status);
            return;
        }
        for (Seat seat : seats) {
            if (seat.getStatus() == SeatStatus.BOOKED) {
                seat.setStatus(SeatStatus.AVAILABLE);
            }
        }
        this.status = BookingStatus.REFUNDED;
        System.out.println("Booking refunded: " + id);
    }

    public String getId() { return id; }
    public User getUser() { return user; }
    public Show getShow() { return show; }
    public List<Seat> getSeats() { return seats; }
    public double getTotalAmount() { return totalAmount; }
    public Payment getPayment() { return payment; }
    public BookingStatus getStatus() { return status; }

    // Static inner Builder class
    public static class BookingBuilder {
        private String id;
        private User user;
        private Show show;
        private List<Seat> seats;
        private double totalAmount;
        private Payment payment;

        public BookingBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public BookingBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public BookingBuilder setShow(Show show) {
            this.show = show;
            return this;
        }

        public BookingBuilder setSeats(List<Seat> seats) {
            this.seats = seats;
            return this;
        }

        public BookingBuilder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public BookingBuilder setPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Booking build() {
            // Validations can be added here
            return new Booking(id, user, show, seats, totalAmount, payment);
        }
    }
}
