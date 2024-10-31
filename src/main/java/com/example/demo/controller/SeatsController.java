package com.example.demo.controller;

import com.example.demo.model.Seats;
import com.example.demo.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
//@CrossOrigin(origins = "http://localhost:3000")
public class SeatsController {

    @Autowired
    private SeatsService seatService;

    @GetMapping("/booked-seats/{idScreening}")
    public ResponseEntity<List<Seats>> getBookedSeats(@PathVariable Long idScreening) {

        List<Seats> seats = seatService.getSeatsByScreeningId(idScreening);
        return ResponseEntity.ok(seats);
    }
}
