package services;

import java.util.ArrayList;
import java.util.List;

import models.Theater;

public class TheaterService {

    private List<Theater> theaters;

    public TheaterService() {
        this.theaters = new ArrayList<>();
    }

    public void addTheater(Theater theater) {
        theaters.add(theater);
    }

    public List<Theater> getAllTheaters() {
        return theaters;
    }

    public Theater getTheaterById(int theaterId){
        return theaters.stream()
                       .filter(theater -> theater.getId() == theaterId)
                       .findFirst()
                       .orElse(null);
    }

   
}
