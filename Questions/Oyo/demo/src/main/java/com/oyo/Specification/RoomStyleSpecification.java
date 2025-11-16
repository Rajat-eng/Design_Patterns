package com.oyo.Specification;

import com.oyo.Enums.RoomStyle;
import com.oyo.Models.Room;

public class RoomStyleSpecification extends AbstractSpecification<Room> {
    private final RoomStyle roomStyle;

    public RoomStyleSpecification(RoomStyle roomStyle){
        this.roomStyle = roomStyle;
    }

    @Override
    public boolean isSatisfiedBy(Room room){
        return room.getStyle() == this.roomStyle;
    }
}
