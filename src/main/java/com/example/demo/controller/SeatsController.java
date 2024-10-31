package com.example.demo.controller;

import com.example.demo.model.Seats;
import com.example.demo.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SeatsController is a REST controller that manages seat-related operations.
 * It provides endpoints for retrieving booked seats for a specific screening.
 */
@RestController
@RequestMapping("/api/seats")
//@CrossOrigin(origins = "http://localhost:3000")
public class SeatsController {

    @Autowired
    private SeatsService seatService;

    /**
     * Retrieves the booked seats for a specific screening by its ID.
     *
     * @param idScreening the ID of the screening for which booked seats are requested
     * @return a ResponseEntity containing the list of booked seats and a 200 OK status
     */
    @GetMapping("/booked-seats/{idScreening}")
    public ResponseEntity<List<Seats>> getBookedSeats(@PathVariable Long idScreening) {

        List<Seats> seats = seatService.getSeatsByScreeningId(idScreening);
        return ResponseEntity.ok(seats);
    }
}