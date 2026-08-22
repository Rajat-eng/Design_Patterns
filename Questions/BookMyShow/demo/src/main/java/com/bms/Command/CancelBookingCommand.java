package com.bms.Command;

import com.bms.Models.Booking;
import com.bms.Services.SeatLockingService;

public class CancelBookingCommand implements BookingCommand {
    private final Booking booking;
    private final SeatLockingService seatLockingService;

    public CancelBookingCommand(Booking booking, SeatLockingService seatLockingService) {
        this.booking = booking;
        this.seatLockingService = seatLockingService;
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

        if (seatLockingService != null) {
            seatLockingService.releaseSeats(booking.getShow(), booking.getSeats(), booking.getUser().getId());
        }

        booking.cancelBooking();
    }
}
