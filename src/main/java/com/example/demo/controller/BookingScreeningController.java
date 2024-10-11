package com.example.demo.controller;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import com.example.demo.model.BookingScreenings;
import com.example.demo.model.Seats;
import com.example.demo.service.BookingScreeningService;
import com.example.demo.service.SeatsService;

import java.util.List;

@RestController
@RequestMapping("/api/booking-screening")
public class BookingScreeningController {

    private final BookingScreeningService bookingScreeningService;

    private final SeatsService seatService;
    private final UserService userService;

    public BookingScreeningController(BookingScreeningService bookingScreeningService, SeatsService seatService, UserService userService) {
        this.bookingScreeningService = bookingScreeningService;
        this.seatService = seatService;
        this.userService = userService;
    }

    @GetMapping
    public List<BookingScreenings> getBookingScreening() {
        return bookingScreeningService.getAllBookingScreenings();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> getBookingScreeningById(@PathVariable Long id) {
        BookingScreenings bookingScreening = bookingScreeningService.getBookingScreeningById(id);
        if (bookingScreening == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking Screening not found.");
        }
        return ResponseEntity.ok(bookingScreening);
    }


    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> createBookingScreening(@RequestBody BookingScreenings bookingScreenings) {
        // Fetch the user from the database before associating
        Users existingUser = userService.getUserById(bookingScreenings.getUser().getIdUser());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
        }

        bookingScreenings.setUser(existingUser);

        bookingScreeningService.saveBookingScreening(bookingScreenings);

        int counter = 0;
        List<Seats> seats = bookingScreenings.getSeats();

        if (seats != null && !seats.isEmpty()) {
            for (Seats seat : seats) {
                seatService.createAndBookSeat(bookingScreenings, seat.getSeatRow(), seat.getSeatCol(), bookingScreenings.getScreening().getIdScreening());
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Booking Screening created successfully: counter: " + counter);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteBookingScreeningById(@PathVariable Long id) {
        BookingScreenings bookingScreening = bookingScreeningService.getBookingScreeningById(id);
        if (bookingScreening == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking Screening not found.");
        }

        // Delete all seats related to this booking screening using the seatService
        seatService.deleteSeatsByBookingScreening(bookingScreening.getIdBookingScreening());

        // Delete the booking screening itself
        bookingScreeningService.deleteBookingScreeningById(id);

        return ResponseEntity.ok("Booking Screening and associated seats deleted successfully.");
    }

    //NO SE PUEDE MODIFICAR UN BOOKINGSCRENEING PORQUE SERIA MUY COMPLICADO, PREGUNTARLE A ROLO SI PRETENDE AGREGARLO
}


