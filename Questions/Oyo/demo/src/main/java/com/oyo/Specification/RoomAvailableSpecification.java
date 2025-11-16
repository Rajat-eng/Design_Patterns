package com.oyo.Specification;

import com.oyo.Models.Room;
import com.oyo.State.AvailableState;

public class RoomAvailableSpecification extends AbstractSpecification<Room>{
    @Override
    public boolean isSatisfiedBy(Room item) {
        return item.getState() instanceof AvailableState;
    }
}
