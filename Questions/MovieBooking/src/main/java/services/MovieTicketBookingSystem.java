package services;

import java.util.ArrayList;
import java.util.List;

import models.Booking;
import models.Movie;
import models.Show;
import models.Theater;
import models.User;

public class MovieTicketBookingSystem {
    private static MovieTicketBookingSystem instance;
    private BookingService bookingService;
    private ShowService showService;
    private TheaterService theaterService;
    private List<Movie> movies;

    public MovieTicketBookingSystem() {
        this.showService = new ShowService();
        this.bookingService = new BookingService(showService);
        this.theaterService = new TheaterService();
        this.movies = new ArrayList<>();
    }

    public static MovieTicketBookingSystem getInstance() {
        if (instance == null) {
            instance = new MovieTicketBookingSystem();
        }
        return instance;
    }

    public void addTheather(Theater theater) {
        theaterService.addTheater(theater);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addShowAndSeats(Show show, List<String> seatIds) {
        showService.addShowAndSeats(show, seatIds);
    }

    public boolean areSeatsAvailable(int showId, List<String> seatIds) {
        return showService.areSeatsAvailable(showId, seatIds);
    }

    public double bookSeatsAndGetPrice(User user, int showId, List<String> seatIds) {
        return bookingService.bookSeatsAndGetPrice(user, showId, seatIds);
    }

    public void showAllAvailableShowsAndSeats() {
        showService.displayAllShows();
    }

    public Show getShow(int showId) {
        return showService.getShow(showId);
    }

    public List<Show> getAllShows() {
        return showService.getAllShows();
    }

    public List<Theater> getAllTheaters() {
        return theaterService.getAllTheaters();
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public void calclateTotalRevenueForAllTheaters() {
        double totalRevenue = 0;
        double totalServiceTax = 0;
        double totalKrishiCess = 0;
        double totalSwatchCess = 0;
        for (Theater theater : getAllTheaters()) {
            List<Booking> bookings = bookingService.getAllBookingsByTheaterId(theater.getId());
            totalRevenue += bookings.stream().mapToDouble(booking -> booking.getTotalPrice()).sum();
            totalServiceTax += bookings.stream().mapToDouble(booking -> booking.getTax().getServiceTax()).sum();
            totalKrishiCess += bookings.stream().mapToDouble(booking -> booking.getTax().getKrishiCess()).sum();
            totalSwatchCess += bookings.stream().mapToDouble(booking -> booking.getTax().getSwatchCess()).sum();
        }
        System.out.println("Revenue: Rs" + totalRevenue);
        System.out.println("Service Tax: Rs" + totalServiceTax);
        System.out.println("Krishi Kalyan Cess: Rs" + totalKrishiCess);
        System.out.println("Swatch Bharat Cess: Rs" + totalSwatchCess);
    }
}
