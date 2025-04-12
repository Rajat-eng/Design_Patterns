package models;

import java.util.List;

import models.enums.BookingStatus;
import strategies.ISeat;

public class Booking {
    private int bookingId;
    private User user;
    private Show show;
    private List<ISeat> bookedSeats;
    private double totalPrice;
    private BookingStatus status;

    public Booking(int bookingId, User user, Show show, List<ISeat> bookedSeats, double totalPrice, BookingStatus status) {
        this.bookingId = bookingId;
        this.user = user;
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public int getId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public Show getShow() {
        return show;
    }

    public List<ISeat> getSeats() {
        return bookedSeats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }
}
