package com.elevator.Strategy;

import java.util.List;
import java.util.Optional;

import com.elevator.Enums.Direction;
import com.elevator.Models.Elevator;
import com.elevator.Models.Request;

public class NearestElevatorStrategy implements ElevatorSelectionStrategy {
    @Override
    public Optional<Elevator> selectElevator(List<Elevator> elevators, Request request) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - request.getTargetFloor());

            // Check if the elevator can serve the request based on its current direction
            if (isSuitable(elevator, request)) {

                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            }
        }

        return Optional.ofNullable(bestElevator);  // Return the best elevator found, or empty if none suitable
    }

    public boolean isSuitable(Elevator elevator, Request request) {
        if (elevator.getDirection() == Direction.IDLE) {
            return true;
        }
        if (elevator.getDirection() == request.getDirection()) {
            if (request.getDirection() == Direction.UP && elevator.getCurrentFloor() <= request.getTargetFloor()) {
                return true;
            }
            if (request.getDirection() == Direction.DOWN && elevator.getCurrentFloor() >= request.getTargetFloor()) {
                return true;
            }
        }
        return false;
    }
    
}
