package com.oyo.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.oyo.Models.Booking;
import com.oyo.Models.Guest;
import com.oyo.Models.Room;

public class BookingService {
    private final List<Booking> bookings = new ArrayList<>();

    public Booking createBooking(Guest guest, Room room, LocalDate startDate, LocalDate endDate) {
        String bookingId = UUID.randomUUID().toString();
        Booking booking = new Booking(bookingId, guest, room, startDate, endDate);

        // Use the State pattern to book the room
        room.book(); 
        bookings.add(booking);
        booking.notifyObservers();
        return booking;
    }

}
