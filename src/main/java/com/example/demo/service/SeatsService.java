package com.example.demo.service;

import com.example.demo.model.BookingScreenings;
import com.example.demo.model.Seats;
import com.example.demo.model.Screenings;
import com.example.demo.repository.SeatsRepository;
import com.example.demo.repository.ScreeningRepository; // Import for the screening repository
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatsService {

    @Autowired
    private SeatsRepository seatsRepository;

    @Autowired
    private ScreeningRepository screeningRepository; // Inject the repository instance

    // Function to return all seats and their states for a given screening
    public List<Seats> getAllSeatsByidScreening(Long idScreening) {
        List<Seats> bookedSeats = seatsRepository.getAllBookedSeatsByidScreening(idScreening);
        return bookedSeats;
    }

    public void createAndBookSeat(Seats seat) {
        seatsRepository.save(seat);
    }

    // You can also implement the method to find by screening ID if needed
    public Seats findBySeatRowAndSeatColAndScreeningId(int seatRow, int seatCol, Long screeningId) {
        return seatsRepository.findBySeatRowAndSeatColAndScreeningId(seatRow, seatCol, screeningId);
    }

    public void deleteSeatsByBookingScreening(Long bookingScreeningId) {
        seatsRepository.deleteByBookingScreeningId(bookingScreeningId);
    }

    public List<Seats> getSeatsByScreeningId(Long screeningId) {
        return seatsRepository.findByScreeningId(screeningId);
    }
}
