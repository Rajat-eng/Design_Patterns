package com.elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.elevator.Enums.Direction;
import com.elevator.Enums.RequestSource;
import com.elevator.Models.Elevator;
import com.elevator.Models.Request;
import com.elevator.Strategy.ElevatorSelectionStrategy;
import com.elevator.Strategy.NearestElevatorStrategy;

public class ElevatorSystem {
    public Map<String,Elevator> elevators;
    public ElevatorSelectionStrategy selectionStrategy;
    public ExecutorService   executorService;
    private static volatile ElevatorSystem instance;

    public static synchronized  ElevatorSystem getInstance(int numElevators){
        if (instance == null) {
            synchronized (ElevatorSystem.class) {
                if (instance == null) {
                    instance = new ElevatorSystem(numElevators);
                }
            }
        }
        return instance;
    }
    public ElevatorSystem(int numElevators) {
        this.selectionStrategy = new NearestElevatorStrategy();
        this.executorService = Executors.newFixedThreadPool(numElevators);
        List<Elevator> elevatorList = new ArrayList<>();
        for(int i=1;i<=numElevators;i++){
            Elevator elevator = new Elevator(i);
            elevatorList.add(elevator);
            
        }

        this.elevators = elevatorList.stream()
                .collect(Collectors.toMap(elevator -> String.valueOf(elevator.getId()), elevator -> elevator));
    }

    public void start() {
        for (Elevator elevator : elevators.values()) {
            executorService.submit(elevator);
        }
    }

    public void requestElevator(int floor, Direction direction) {
        Request request = new Request(floor, direction, RequestSource.EXTERNAL);
        Optional<Elevator> selectedElevator = selectionStrategy.selectElevator(new ArrayList<>(elevators.values()), request);
        if(selectedElevator.isPresent()) {
            selectedElevator.get().addRequest(request);
            System.out.println("Elevator " + selectedElevator.get().getId() + " assigned to floor " + floor + " going " + direction);
        } else {
            System.out.println("No suitable elevator found for floor " + floor + " going " + direction);
        }
    }

    // INTERNAL Request (Cabin Call)
    public void selectFloor(int elevatorId, int destinationFloor) {
        System.out.println("\n>> INTERNAL Request: User in Elevator " + elevatorId + " selected floor " + destinationFloor);
        Request request = new Request(destinationFloor, Direction.IDLE, RequestSource.INTERNAL);

        Elevator elevator = elevators.get(elevatorId);
        if (elevator != null) {
            elevator.addRequest(request);
        } else {
            System.err.println("Invalid elevator ID.");
        }
    }

    public void shutdown() {
        System.out.println("Shutting down elevator system...");
        for (Elevator elevator : elevators.values()) {
            elevator.stopElevator();
        }
        executorService.shutdown();
    }


}
