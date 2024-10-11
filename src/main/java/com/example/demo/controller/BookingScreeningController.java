package com.example.demo.controller;

import com.example.demo.model.BookingScreenings;
import com.example.demo.model.Seats;
import com.example.demo.service.BookingScreeningService;
import com.example.demo.service.SeatsService;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;

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




    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> createBookingScreening(@RequestBody BookingScreenings bookingScreenings) {
        // Fetch the user from the database before associating
        Users existingUser = userService.getUserById(bookingScreenings.getUser().getIdUser());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
        }

        // Ensure the user is associated properly
        bookingScreenings.setUser(existingUser);

        // Save the booking
        bookingScreeningService.saveBookingScreening(bookingScreenings);

        int counter = 0;
        List<Seats> seats = bookingScreenings.getSeats();

        if (seats != null && !seats.isEmpty()) { //esto con la validacion no funcionaba asi que lo tuve que comentar, despues arreglar eso
            for (Seats seat : seats) {
                //Seats existingSeat = seatService.findSeatByRowAndCol(seat.getSeatRow(), seat.getSeatCol());
                //if (existingSeat != null && !existingSeat.isBooked()) {
                seatService.createAndBookSeat(bookingScreenings, seat.getSeatRow(), seat.getSeatCol(), bookingScreenings.getScreening().getIdScreening());
                counter++;
                //} else {
                //    return ResponseEntity.status(HttpStatus.CONFLICT).body("Seat " + seat.getSeatRow() + "-" + seat.getSeatCol() + " is already booked.");
                }
            }
        //}

        return ResponseEntity.status(HttpStatus.CREATED).body("Booking Screening created successfully: counter: " + counter);
    }





}


