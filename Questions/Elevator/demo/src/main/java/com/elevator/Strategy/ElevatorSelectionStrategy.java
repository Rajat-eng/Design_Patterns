package com.elevator.Strategy;

import java.util.List;
import java.util.Optional;

import com.elevator.Models.Elevator;
import com.elevator.Models.Request;

public interface ElevatorSelectionStrategy {
    Optional<Elevator> selectElevator(List<Elevator> elevators, Request request);
}
