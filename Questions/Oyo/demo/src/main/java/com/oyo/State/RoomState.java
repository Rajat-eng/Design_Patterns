package com.oyo.State;

import com.oyo.Models.Room;

public interface RoomState {
    void book(Room room);
    void checkIn(Room room);
    void checkOut(Room room);
    void markForMaintenance(Room room);
}
