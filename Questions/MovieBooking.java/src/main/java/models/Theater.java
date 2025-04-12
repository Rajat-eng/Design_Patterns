package models;

import java.util.ArrayList;
import java.util.List;

public class Theater {
    private int id;
    private final String name;
    private final String location;
    private final List<Show> shows;

    public Theater(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.shows = new ArrayList<>();
    }

    public void addShows(List<Show> shows) {
        this.shows.addAll(shows);
    }
    public int getId() {
        return id;
    }
}
