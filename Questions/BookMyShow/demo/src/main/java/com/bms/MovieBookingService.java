package com.bms;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.bms.Models.Booking;
import com.bms.Models.City;
import com.bms.Models.Movie;
import com.bms.Models.Screen;
import com.bms.Models.Seat.Seat;
import com.bms.Models.Show;
import com.bms.Models.Theater;
import com.bms.Models.User;
import com.bms.Services.BookingService;
import com.bms.Services.SeatLockingService;
import com.bms.Strategy.payment.PaymentStrategy;
import com.bms.Strategy.pricing.PricingStrategy;

public class MovieBookingService {
    private static volatile MovieBookingService instance;

    private final Map<String, City> cities;
    private final Map<String, Theater> cinemas;
    private final Map<String, Movie> movies;
    private final Map<String, User> users;
    private final Map<String, Show> shows;

    // Core services - managed by the system
    private final SeatLockingService seatLockManager;
    private final BookingService bookingManager;

     private MovieBookingService() {
        this.cities = new ConcurrentHashMap<>();
        this.cinemas = new ConcurrentHashMap<>();
        this.movies = new ConcurrentHashMap<>();
        this.users = new ConcurrentHashMap<>();
        this.shows = new ConcurrentHashMap<>();

        this.seatLockManager = new SeatLockingService();
        this.bookingManager = new BookingService(seatLockManager);
    }

    public static MovieBookingService getInstance() {
        if (instance == null) {
            synchronized (MovieBookingService.class) {
                if (instance == null) {
                    instance = new MovieBookingService();
                }
            }
        }
        return instance;
    }

    public City addCity(String id, String name) {
        City city = new City(id, name);
        cities.put(city.getId(), city);
        return city;
    }

    public Theater addCinema(String id, String name, String cityId, List<Screen> screens) {
        City city = cities.get(cityId);
        Theater cinema = new Theater(id, name, city, screens);
        cinemas.put(cinema.getId(), cinema);
        return cinema;
    }

    public void addMovie(Movie movie) {
        this.movies.put(movie.getId(), movie);
    }

    public Show addShow(String id, Movie movie, Screen screen, LocalDateTime startTime, PricingStrategy pricingStrategy) {
        Show show = new Show(id, movie, screen, startTime, pricingStrategy);
        shows.put(show.getId(), show);
        return show;
    }

    public User createUser(String name, String email) {
        User user = new User(name, email);
        users.put(user.getId(), user);
        return user;
    }

    public Optional<Booking> bookTickets(String userId, String showId, List<Seat> desiredSeats, PaymentStrategy paymentStrategy) {
        return bookingManager.createBooking(
                users.get(userId),
                shows.get(showId),
                desiredSeats,
                paymentStrategy
        );
    }

    // --- Search Functionality ---
    public List<Show> findShows(String movieTitle, String cityName) {
        List<Show> result = new ArrayList<>();
        shows.values().stream()
            .filter(show -> show.getMovie().getTitle().equalsIgnoreCase(movieTitle))
            .filter(show -> show.getScreen().getTheater().getCity().getName().equalsIgnoreCase(cityName))
            .forEach(result::add);
        return result;
    }

    public void shutdown() {
        this.seatLockManager.shutDown();
        System.out.println("MovieTicketBookingSystem has been shut down.");
    }

}
