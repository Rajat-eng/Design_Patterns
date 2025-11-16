package com.oyo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oyo.Models.Room;
import com.oyo.Specification.Specification;

public class RoomService {
private final List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> findRooms(Specification<Room> spec) {
        return rooms.stream()
                .filter(room -> spec.isSatisfiedBy(room))
                .collect(Collectors.toList());
    }

    public Room findRoomByNumber(String roomNumber) {
        return rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst()
                .orElse(null);
    }
}
