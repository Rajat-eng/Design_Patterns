package models;

import java.util.List;
import java.util.UUID;

import models.enums.BookingStatus;
import strategies.ISeat;

public class Booking {
    private String bookingId;
    private User user;
    private Show show;
    private List<ISeat> bookedSeats;
    private double totalPrice;
    private BookingStatus status;
    private Theater theater;
    private Tax tax;

    public Booking(User user, Theater theater, Show show, List<ISeat> bookedSeats, double totalPrice, BookingStatus status) {
        this.bookingId = UUID.randomUUID().toString();
        this.user = user;
        this.theater = theater;
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Theater getTheater(){
        return this.theater;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getId() {
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

    public void setTax(Tax tax){
        this.tax = tax;
    }

    public Tax getTax(){
        return tax;
    }
}
