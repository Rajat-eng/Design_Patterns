package com.oyo.Strategy;

import com.oyo.Models.Booking;

public interface NotificationStrategy {
    void sendNotification(Booking booking);
}
