package com.bms.Services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.bms.Enums.SeatStatus;
import com.bms.Models.Seat.Seat;
import com.bms.Models.Show;

public class SeatLockingService {
    private Map<Show,Map<Seat,String>> lockedSeats = new ConcurrentHashMap<>();
    public ScheduledExecutorService scheduler= Executors.newScheduledThreadPool(1);
    public boolean lockSeats(Show show,List<Seat> seats,String userId){
        synchronized (show) {
            for(Seat seat:seats){
                 if (seat.getStatus() != SeatStatus.AVAILABLE) {
                    System.out.println("Seat " + seat.getId() + " is not available.");
                    return false;
                }
            }
            for (Seat seat : seats) {
                seat.setStatus(SeatStatus.LOCKED);
            }
            lockedSeats.computeIfAbsent(show, k-> new ConcurrentHashMap<>());
            for(Seat seat:seats){
                lockedSeats.get(show).put(seat,userId);
            }

            scheduler.schedule(()->{
                unlockSeats(show, seats, userId);
            }, 1,TimeUnit.SECONDS );

            System.out.println("Locked seats: " + seats.stream().map(Seat::getId).toList() + " for user " + userId);
            return true;
        }
    }

    public void unlockSeats(Show show, List<Seat> seats,String userId){
        synchronized (show) {
            Map<Seat,String> showLocks = lockedSeats.get(show);
            if(showLocks!=null){
                for(Seat seat:seats){
                    if(showLocks.containsKey(seat) && showLocks.get(seat).equals(userId)){
                        showLocks.remove(seat);
                        if(seat.getStatus() == SeatStatus.LOCKED){ // seats remin locked while booking
                            seat.setStatus(SeatStatus.AVAILABLE);
                            System.out.println("seats count not be booked. There is a timeout");
                        }else{
                            System.out.println("seats booked");
                        }
                    }
                }
            }
        }
    }

    public void shutDown(){
        System.out.println("Shutting down SeatLockProvider scheduler.");
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) { // not closed under 5 sec
                scheduler.shutdownNow(); 
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

