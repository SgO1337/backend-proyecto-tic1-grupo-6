package com.example.demo.controller;

import com.example.demo.model.Seats;
import com.example.demo.model.Screenings;
import com.example.demo.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Change to RestController for JSON responses
@RequestMapping("/api/seats")  // Base URL for the API
//@CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for your React app
public class SeatsController {

    @Autowired
    private SeatsService seatService;

    // Get all seats for a specific screening
    @GetMapping("/all")
    public ResponseEntity<List<Seats>> getAllSeats(@RequestParam Long idScreening) {
        List<Seats> seats = seatService.getAllSeatsByidScreening(idScreening);
        return ResponseEntity.ok(seats);
    }


    @GetMapping("/booked-seats/{idScreening}")
    public ResponseEntity<List<Seats>> getBookedSeats(@PathVariable Long idScreening) {
        
        List<Seats> seats = seatService.getSeatsByScreeningId(idScreening);
        return ResponseEntity.ok(seats);
    }
/*
    // Book a seat for a specific screening
    @PutMapping("/book")
    public ResponseEntity<String> bookSeat(@RequestParam Long idScreening, @RequestParam Long seatId) {
        boolean isBooked = seatService.bookSeat(idScreening, seatId);
        if (isBooked) {
            return ResponseEntity.ok("Seat booked successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Seat is already booked or not found.");
        }
    }*/

    /*
    // Create seats for a specific screening
    @PostMapping("/createSeatsForScreening")
    public ResponseEntity<String> createSeatsForScreening(@RequestParam Long idScreening) {
        // Ensure the screening exists before creating seats
        Screenings screening = seatService.getScreeningById(idScreening); // Assuming you have this method in your service
        if (screening == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Screening not found.");
        }

        seatService.createSeatsForScreening(idScreening);
        return ResponseEntity.ok("Seats created successfully.");
    }*/
/*
    // Unbook a seat for a specific screening
    @PutMapping("/unbook")
    public ResponseEntity<String> unbookSeat(@RequestParam Long idScreening, @RequestParam Long seatId) {
        boolean isUnbooked = seatService.unbookSeat(idScreening, seatId);
        if (isUnbooked) {
            return ResponseEntity.ok("Seat unbooked successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Seat is already unbooked or not found.");
        }
    }*/
}
