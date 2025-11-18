package com.oyo.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.oyo.Enums.BookingStatus;
import com.oyo.Observer.BookingObserver;

public class Booking  {
    private final String bookingId;
    private final Guest guest;
    private final Room room;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private BookingStatus status;
    private List<BookingObserver> observers = new ArrayList<>();

    public Booking(String bookingId, Guest guest, Room room, LocalDate startDate, LocalDate endDate) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = BookingStatus.CONFIRMED;
        addObserver(guest);
    }

    public void checkIn() { this.status = BookingStatus.CHECKED_IN; }
    public void checkOut() { this.status = BookingStatus.CHECKED_OUT; }
    public void cancel() { this.status = BookingStatus.CANCELLED; }

    public String getBookingId() { return bookingId; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public BookingStatus getStatus() { return status; }

    public void addObserver(BookingObserver observer){
        observers.add(observer);
    }

    public void notifyObservers(){
        for(BookingObserver observer:observers){
            observer.update(this);
        }
    }
}
