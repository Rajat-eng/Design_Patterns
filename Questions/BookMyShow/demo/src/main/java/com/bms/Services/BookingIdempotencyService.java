package com.bms.Services;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.bms.Models.Seat.Seat;
import com.bms.Models.Show;
import com.bms.Models.User;

public class BookingIdempotencyService {
    private final Set<String> completedRequests = ConcurrentHashMap.newKeySet();

    public String createKey(User user, Show show, List<Seat> seats) {
        String seatIds = seats.stream()
                .map(Seat::getId)
                .sorted()
                .collect(Collectors.joining(","));
        return user.getId() + "|" + show.getId() + "|" + seatIds;
    }

    public boolean isDuplicate(User user, Show show, List<Seat> seats) {
        String key = createKey(user, show, seats);
        return !completedRequests.add(key);
    }

    public void markCompleted(User user, Show show, List<Seat> seats) {
        completedRequests.add(createKey(user, show, seats));
    }
}
