package com.uber.Observer;

import com.uber.Models.Trip;

public interface TripObserver {

    void onUpdate(Trip trip);

}
