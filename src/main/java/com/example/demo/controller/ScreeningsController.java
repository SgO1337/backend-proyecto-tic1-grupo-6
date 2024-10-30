package com.example.demo.controller;

import com.example.demo.model.Screenings;
import com.example.demo.service.MoviesService;
import com.example.demo.service.RoomsService;
import com.example.demo.service.ScreeningService;
import com.example.demo.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screenings")
//@CrossOrigin(origins = "http://localhost:3000") //uncomment when going into prod
public class ScreeningsController {

    @Autowired
    private final ScreeningService screeningService;

    @Autowired
    public ScreeningsController(ScreeningService screeningService, MoviesService movieService, RoomsService roomService, SeatsService seatService) {
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    @Autowired
    private MoviesService movieService;

    @Autowired
    private RoomsService roomService;

    // List all screenings
    @GetMapping
    public ResponseEntity<List<Screenings>> listScreenings() {
        List<Screenings> screenings = screeningService.getAllScreenings();
        return ResponseEntity.ok(screenings);
    }

    // View screening by ID
    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewScreening(@PathVariable Long id) {
        Screenings screening = screeningService.getScreeningById(id);
        if (screening == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Screening not found.");
        }
        return ResponseEntity.ok(screening);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createScreening(@RequestBody Screenings screening) {
        // Verificar si el Room existe antes de crear el screening
        Long roomId = screening.getRoom().getIdRoom();


        if (!roomService.existsById(roomId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested room wasn't found.");
        }

        if(!movieService.existsById(screening.getMovie().getIdMovie())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested movie wasn't found.");
        }

        try {
            Screenings createdScreening = screeningService.createScreening(screening);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdScreening);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }



    // Update an existing screening
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateScreening(@PathVariable Long id, @RequestBody Screenings updatedScreening) {
        Screenings existingScreening = screeningService.getScreeningById(id);
        if (existingScreening == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Screening not found.");
        }

        if(!movieService.existsById(updatedScreening.getMovie().getIdMovie())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested movie wasn't found.");
        }

        if(!roomService.existsById(updatedScreening.getRoom().getIdRoom())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested room wasn't found.");
        }

        // Update screening details
        existingScreening.setDate(updatedScreening.getDate());
        existingScreening.setLanguage(updatedScreening.getLanguage());
        existingScreening.setSubtitles(updatedScreening.getSubtitles());
        existingScreening.setTime(updatedScreening.getTime());
        existingScreening.setMovie(updatedScreening.getMovie());

        Screenings updated = screeningService.saveScreening(existingScreening);
        return ResponseEntity.ok(updated);
    }

    // Delete a screening
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteScreening(@PathVariable Long id) {
        Screenings screening = screeningService.getScreeningById(id);
        if (screening == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Screening not found.");
        }

        screeningService.deleteScreening(id);
        return ResponseEntity.ok("Screening deleted successfully.");
    }
}
