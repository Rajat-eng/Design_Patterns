package com.elevator.State;

import com.elevator.Enums.Direction;
import com.elevator.Models.Elevator;
import com.elevator.Models.Request;

public interface ElevatorState {
    void move(Elevator elevator);
    void addRequest(Elevator elevator, Request request);
    Direction getDirection();
}