package services;

import java.util.ArrayList;
import java.util.List;

import models.Movie;
import models.Show;
import models.Theater;
import models.User;

public class MovieTicketBookingSystem {
    private static MovieTicketBookingSystem instance;
    private BookingService bookingService;
    private ShowService showService;
    private List<Theater> theaters;
    private List<Movie> movies;

    public MovieTicketBookingSystem() {
        
        this.showService = new ShowService();
        this.bookingService = new BookingService(showService);
        this.movies=new ArrayList<>();
        this.theaters=new ArrayList<>();
    }

    public static MovieTicketBookingSystem getInstance(){
        if(instance==null){
            instance=new MovieTicketBookingSystem();
        }
        return instance;
    }

    public void addTheather(Theater theater){
        theaters.add(theater);
    }

    public void addMovie(Movie movie){
        movies.add(movie);
    }

    public void addShowAndSeats(int showId , List<String> seatIds){
        showService.addShowAndSeats(showId, seatIds);
    }

    public void addShowsToTheater(Theater theater, List<Show> shows){
        theater.addShows(shows);
    }

    public boolean areSeatsAvailable(int showId,List<String> seatIds) {
        return showService.areSeatsAvailable(showId, seatIds);
    }

    public double bookSeatsAndGetPrice(User user,int showId, List<String> seatIds) {
        return bookingService.bookSeatsAndGetPrice(user,showId, seatIds);
    }   
    
    public void showAllAvailableShowsAndSeats(){
        showService.displayAllShows();
    }

    public Show getShow(int showId){
        return showService.getShow(showId);
    }

    public List<Show> getAllShows(){
        return showService.getAllShows();
    }
}
