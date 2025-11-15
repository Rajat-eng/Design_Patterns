import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.Movie;
import models.Show;
import models.Theater;
import models.User;
import services.MovieTicketBookingSystem;

public class Start {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        User user = new User("rv@gmail.com");

        Theater theater1 = new Theater(1, "t1", "Delhi");
        Theater theater2 = new Theater(2, "t2", "Pune");

        Show show1 = new Show(1);
        Show show2 = new Show(2);
        Show show3 = new Show(3);
        List<String> seatList1 = Arrays
                .asList("A1,A2,A3,A4,A5,A6,A7,A8,A9,B1,B2,B3,B4,B5,B6,C2,C3,C4,C5,C6,C7".split(","));
        List<String> seatList2 = Arrays.asList("A1,A2,A3,A4,A5,A6,A7,B2,B3,B4,B5,B6,C1,C2,C3,C4,C5,C6,C7".split(","));
        List<String> seatList3 = Arrays
                .asList("A1,A2,A3,A4,A5,A6,A7,B2,B3,B4,B5,B6,B7,B8,C1,C2,C3,C4,C5,C6,C7,C8,C9".split(","));

        Movie movie = new Movie("XXX", "YYY", 120, "English", "ADA");
        
        show1.setMovieAndTheaterForTheShow(movie, theater1);
        show2.setMovieAndTheaterForTheShow(movie, theater1);
        show3.setMovieAndTheaterForTheShow(movie, theater2);


        MovieTicketBookingSystem movieTicketBookingSystem = MovieTicketBookingSystem.getInstance();
        movieTicketBookingSystem.addTheather(theater1);
        movieTicketBookingSystem.addTheather(theater2);
        movieTicketBookingSystem.addMovie(movie);

        movieTicketBookingSystem.addShowAndSeats(show1, seatList1);
        movieTicketBookingSystem.addShowAndSeats(show2, seatList2);
        movieTicketBookingSystem.addShowAndSeats(show3, seatList3);

        try {
            movieTicketBookingSystem.showAllAvailableShowsAndSeats();
            while (true) {
                System.out.println("Enter Show Number:");
                String showNumber = scanner.next();
                int showId = Integer.parseInt(showNumber);
                if (showId < 1 || showId > movieTicketBookingSystem.getAllShows().size()) {
                    System.out.println("Invalid Show Number. Please try again.");
                }
                System.out.println("Available Seats:");
                movieTicketBookingSystem.showAllAvailableShowsAndSeats();
                System.out.println("Enter Seat IDs to book (comma separated) or 'exit' to quit:");
                String input = scanner.next();

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                String[] seatIds = input.split(",");
                for (int i = 0; i < seatIds.length; i++) {
                    seatIds[i] = seatIds[i].trim();
                }

                List<String> seatsToBook = List.of(seatIds);

                movieTicketBookingSystem.bookSeatsAndGetPrice(user, showId, seatsToBook);
                
                System.out.println("Would you like to try again? (Yes/No)");
                String tryAgain = scanner.next();
                if (tryAgain.equalsIgnoreCase("No")) {
                    movieTicketBookingSystem.calclateTotalRevenueForAllTheaters();
                    break;
                } 
            }

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid Show ID." + e.getMessage());
            scanner.nextLine();
        }

    }
}
