package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.BookingScreeningRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingScreeningService {
    private final BookingScreeningRepository bookingScreeningRepository;

    public BookingScreeningService(BookingScreeningRepository bookingScreeningRepository) {
        this.bookingScreeningRepository = bookingScreeningRepository;
    }

    public List<BookingScreenings> getBookingScreeningByUserId(Long id) {
        return bookingScreeningRepository.getBookingScreeningByUserId(id);
    }

    @Transactional
    public BookingScreenings saveBookingScreening(BookingScreenings bookingScreenings){
        return  bookingScreeningRepository.save(bookingScreenings);
    }

    public BookingScreenings getBookingScreeningById(Long id) {
        return bookingScreeningRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteBookingScreeningById(Long id) {
        Optional<BookingScreenings> bookingScreenings = bookingScreeningRepository.findById(id);
        bookingScreenings.ifPresent(m -> bookingScreeningRepository.deleteById(id));
    }

    public List<BookingScreenings> getAllBookingScreenings(){
        return bookingScreeningRepository.findAll();
    }


}
