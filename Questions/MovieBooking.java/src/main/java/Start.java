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

        Movie movie = new Movie("XXX", "YYY", 120, "English", "ADA");

        MovieTicketBookingSystem movieTicketBookingSystem = MovieTicketBookingSystem.getInstance();
        movieTicketBookingSystem.addMovie(movie);

        movieTicketBookingSystem.addShowsToTheater(theater1, Arrays.asList(show1, show2, show3));
        movieTicketBookingSystem.addShowsToTheater(theater2, Arrays.asList(show1, show2, show3));

        List<String> allSeats = Arrays.asList("G1,G2,G3,G4,G5,S1,S2,S3,S4,S5,P1,P2,P3,P4,P5".split(","));
        movieTicketBookingSystem.addShowAndSeats(show1.getShowID(), allSeats);
        movieTicketBookingSystem.addShowAndSeats(show2.getShowID(), allSeats);
        movieTicketBookingSystem.addShowAndSeats(show3.getShowID(), allSeats);

        while (true) {
            try {
                movieTicketBookingSystem.showAllAvailableShowsAndSeats();
                System.out.println("Enter Show Number:");
                String showNumber = scanner.next();
                if (showNumber.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the application.");
                    break;
                }
                int showId = Integer.parseInt(showNumber);
                if (showId < 1 || showId > movieTicketBookingSystem.getAllShows().size()) {
                    System.out.println("Invalid Show Number. Please try again.");
                    continue;
                }

                if (movieTicketBookingSystem.getShow(showId) == null) {
                    System.out.println("Invalid Show ID. Please try again.");
                    continue;
                }

                while (true) {
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

                    if (movieTicketBookingSystem.areSeatsAvailable(showId, seatsToBook)) {
                        double totalPrice = movieTicketBookingSystem.bookSeatsAndGetPrice(user, showId, seatsToBook);
                        System.out.println("Total amount after taxes: " + totalPrice);
                    } else {
                        System.out.println("Some seats are not available. Please try again.");
                    }
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid Show ID." + e.getMessage());
                scanner.nextLine(); 
            }
        }
    }
}
