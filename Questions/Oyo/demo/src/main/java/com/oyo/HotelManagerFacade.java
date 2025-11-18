package com.oyo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.oyo.Decorator.Bookable;
import com.oyo.Decorator.BreakfastDecorator;
import com.oyo.Decorator.RoomBooking;
import com.oyo.Decorator.SpaDecorator;
import com.oyo.Enums.RoomStyle;
import com.oyo.Enums.RoomType;
import com.oyo.Models.Booking;
import com.oyo.Models.Guest;
import com.oyo.Models.Room;
import com.oyo.Services.BookingService;
import com.oyo.Services.PaymentService;
import com.oyo.Services.RoomService;
import com.oyo.Specification.RoomAvailableSpecification;
import com.oyo.Specification.RoomStyleSpecification;
import com.oyo.Specification.RoomTypeSpecification;
import com.oyo.Specification.Specification;

public class HotelManagerFacade {
    private final RoomService roomService;
    private final BookingService bookingService;
    private final PaymentService paymentService;
    

    public HotelManagerFacade(RoomService roomService, BookingService bookingService, PaymentService paymentService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
        this.paymentService = paymentService;
    }

    public Booking bookRoom(Guest guest, RoomType roomType, RoomStyle roomStyle, LocalDate start, LocalDate end, List<String> Amenities){
        Specification<Room> searchSpec = new RoomAvailableSpecification()
        .and(new RoomTypeSpecification(roomType))
        .and(new RoomStyleSpecification(roomStyle));

        Optional<Room> availableRooms = roomService.findRooms(searchSpec).stream().findFirst();

        if(availableRooms.isPresent()){
            Room room = availableRooms.get();
            Bookable bookRoom = new RoomBooking(room);
            for(String facility:Amenities){
                if("breakfast".equalsIgnoreCase(facility)){
                    bookRoom = new BreakfastDecorator(bookRoom);
                }
                if("spa".equalsIgnoreCase(facility)){
                    bookRoom = new SpaDecorator(bookRoom);
                }
            }
            double amount = bookRoom.getCost();
            paymentService.processPayment(amount);
            Booking booking = bookingService.createBooking(guest, room, start, end);
            return booking;
        }else{
            System.out.println("Sorry, no rooms available matching your criteria.");
            return null;
        }
    }

    public void checkIn(String bookingId) {
        // In a real system, you'd fetch the booking by ID
        // For this demo, we'll find a room and check it in
        System.out.println("Check-in process for booking ID (not implemented for demo): " + bookingId);
    }

    public void checkOut(String roomNumber) {
        Room room = roomService.findRoomByNumber(roomNumber);
        if(room != null) {
            room.checkOut();
        } else {
            System.out.println("Room " + roomNumber + " not found.");
        }
    }
}
