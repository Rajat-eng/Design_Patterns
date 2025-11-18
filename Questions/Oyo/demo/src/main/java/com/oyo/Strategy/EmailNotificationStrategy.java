package com.oyo.Strategy;

import com.oyo.Models.Booking;

public class EmailNotificationStrategy implements NotificationStrategy {
    @Override
    public void sendNotification(Booking booking) {
        System.out.println("Sending EMAIL for booking: " + booking.getBookingId());
    }
}
