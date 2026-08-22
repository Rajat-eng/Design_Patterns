package com.bms.Services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.bms.Enums.SeatStatus;
import com.bms.Models.Seat.Seat;
import com.bms.Models.Show;

public class SeatLockingService {
    private final Map<Show, Map<Seat, String>> lockedSeats = new ConcurrentHashMap<>();
    private final Map<Show, ReentrantLock> showLocks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    private ReentrantLock getShowLock(Show show) {
        return showLocks.computeIfAbsent(show, key -> new ReentrantLock());
    }

    public boolean lockSeats(Show show, List<Seat> seats, String userId) {
        return lockSeats(show, seats, userId, 2, TimeUnit.SECONDS);
    }

    public boolean lockSeats(Show show, List<Seat> seats, String userId, long timeout, TimeUnit unit) {
        ReentrantLock lock = getShowLock(show);
        try {
            if (!lock.tryLock(timeout, unit)) {
                System.out.println("Could not acquire lock for show " + show.getId() + " within timeout.");
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted while waiting for show lock.");
            return false;
        }

        try {
            for (Seat seat : seats) {
                if (seat.getStatus() != SeatStatus.AVAILABLE) {
                    System.out.println("Seat " + seat.getId() + " is not available.");
                    return false;
                }
            }

            Map<Seat, String> showSeatMap = lockedSeats.computeIfAbsent(show, key -> new ConcurrentHashMap<>());
            for (Seat seat : seats) {
                seat.setStatus(SeatStatus.LOCKED);
                showSeatMap.put(seat, userId);
            }

            scheduler.schedule(() -> expireLock(show, seats, userId), 30, TimeUnit.SECONDS);
            System.out.println("Locked seats: " + seats.stream().map(Seat::getId).toList() + " for user " + userId);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void releaseSeats(Show show, List<Seat> seats, String userId) {
        ReentrantLock lock = getShowLock(show);
        lock.lock();
        try {
            Map<Seat, String> showLocksBySeat = lockedSeats.get(show);
            if (showLocksBySeat == null) {
                return;
            }

            for (Seat seat : seats) {
                String owner = showLocksBySeat.get(seat);
                if (owner != null && owner.equals(userId)) {
                    showLocksBySeat.remove(seat);
                    if (seat.getStatus() == SeatStatus.LOCKED) {
                        seat.setStatus(SeatStatus.AVAILABLE);
                        System.out.println("Seat " + seat.getId() + " released because lock expired or booking was cancelled.");
                    }
                }
            }

            if (showLocksBySeat.isEmpty()) {
                lockedSeats.remove(show);
            }
        } finally {
            lock.unlock();
        }
    }

    public void confirmSeats(Show show, List<Seat> seats, String userId) {
        ReentrantLock lock = getShowLock(show);
        lock.lock();
        try {
            Map<Seat, String> showLocksBySeat = lockedSeats.get(show);
            if (showLocksBySeat == null) {
                return;
            }

            for (Seat seat : seats) {
                String owner = showLocksBySeat.get(seat);
                if (owner != null && owner.equals(userId)) {
                    showLocksBySeat.remove(seat);
                    seat.setStatus(SeatStatus.BOOKED);
                    System.out.println("Seat " + seat.getId() + " confirmed for user " + userId);
                }
            }

            if (showLocksBySeat.isEmpty()) {
                lockedSeats.remove(show);
            }
        } finally {
            lock.unlock();
        }
    }

    private void expireLock(Show show, List<Seat> seats, String userId) {
        ReentrantLock lock = getShowLock(show);
        lock.lock();
        try {
            Map<Seat, String> showLocksBySeat = lockedSeats.get(show);
            if (showLocksBySeat == null) {
                return;
            }

            for (Seat seat : seats) {
                String owner = showLocksBySeat.get(seat);
                if (owner != null && owner.equals(userId) && seat.getStatus() == SeatStatus.LOCKED) {
                    showLocksBySeat.remove(seat);
                    seat.setStatus(SeatStatus.AVAILABLE);
                    System.out.println("Lock expired for seat " + seat.getId() + " and it is now available again.");
                }
            }

            if (showLocksBySeat.isEmpty()) {
                lockedSeats.remove(show);
            }
        } finally {
            lock.unlock();
        }
    }

    public void shutDown() {
        System.out.println("Shutting down SeatLockProvider scheduler.");
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

