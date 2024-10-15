package com.example.demo.service;

import com.example.demo.model.Rooms;
import com.example.demo.model.Screenings;
import com.example.demo.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.example.demo.service.RoomsService;

import jakarta.transaction.Transactional;


@Service
public class ScreeningService {

    @Autowired
    private final ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public List<Screenings> getAllScreenings() {
        return screeningRepository.findAll();
    }

    public Screenings getScreeningById(Long id) {
        return screeningRepository.findById(id).orElse(null);
    }

    @Transactional
    public Screenings saveScreening(Screenings screening) {
       return screeningRepository.save(screening);
    }

    @Transactional
    public void deleteScreening(Long id) {
        screeningRepository.deleteById(id);
    }

    public Screenings createScreening(Screenings screening) {


        // Check if a screening exists with the same movie and room but different time
        List<Screenings> existingScreenings = screeningRepository.findByMovieAndRoom(
                screening.getMovie(), screening.getRoom());

        for (Screenings existing : existingScreenings) {
            if (existing.getDate().equals(screening.getDate()) && existing.getTime().equals(screening.getTime())) {
                throw new IllegalArgumentException("A screening with the same movie, room, and time already exists.");
            }
        }

        if (screening.getRoom().getIdRoom() == null || screening.getMovie().getIdMovie() == null) {
            throw new IllegalArgumentException("Room or movie ID is missing.");
        }

        if (screening.getDate() == null || screening.getTime() == null) {
            throw new IllegalArgumentException("Date or time is missing.");
        }






        // Save the new screening if it passes the checks
        return screeningRepository.save(screening);
    }
}
