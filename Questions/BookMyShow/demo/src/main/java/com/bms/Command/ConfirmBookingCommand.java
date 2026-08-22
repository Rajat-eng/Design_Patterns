package com.bms.Command;

import com.bms.Models.Booking;

public class ConfirmBookingCommand implements BookingCommand {
    private final Booking booking;

    public ConfirmBookingCommand(Booking booking) {
        this.booking = booking;
    }

    @Override
    public boolean isValid() {
        return booking != null;
    }

    @Override
    public void execute() {
        if (!isValid()) {
            System.out.println("Booking cannot be null.");
            return;
        }
        booking.confirmBooking();
    }
}
