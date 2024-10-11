package com.example.demo.controller;

import com.example.demo.model.Movies;
import com.example.demo.service.MoviesService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
// @CrossOrigin(origins = "http://localhost:3000") // Uncomment for production
public class MoviesController {

    private final MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    // List all movies
    @GetMapping
    public ResponseEntity<List<Movies>> listMovies() {
        List<Movies> movies = moviesService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    // List currently available movies
    @GetMapping("/currently-available")
    public ResponseEntity<List<Movies>> listAvailableMovies() {
        List<Movies> availableMovies = moviesService.getAllAvailableMovies();
        return ResponseEntity.ok(availableMovies);
    }

    // View a specific movie by ID
    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewMovie(@PathVariable Long id) {
        Movies movie = moviesService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }
        return ResponseEntity.ok(movie);
    }

    // Create a new movie
    @PostMapping("/create")
    public ResponseEntity<String> createMovie(@RequestBody Movies movie) {
        if (movie.getTitle() == null || movie.getTitle().isEmpty() || movie.getDescription() == null || movie.getDescription().isEmpty() || movie.getGenre() == null || movie.getGenre().isEmpty() || movie.getDuration() == 0 || movie.getDirector() == null || movie.getDirector().isEmpty() || movie.getCasting() == null || movie.getCasting().isEmpty() || movie.getReleaseDate() == null || movie.getLanguagesAvailable() == null || movie.getLanguagesAvailable().isEmpty() || movie.getRating() == null || movie.getRating().isEmpty() || movie.getDistributor() == null || movie.getDistributor().isEmpty() || movie.getDimensionsAvailable() == null || movie.getDimensionsAvailable().isEmpty() || movie.getVerticalPosterBASE64() == null || movie.getVerticalPosterBASE64().isEmpty() || movie.getHorizontalPosterBASE64() == null || movie.getHorizontalPosterBASE64().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incomplete set of data.");
        }

        moviesService.saveMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body("Movie created successfully.");
    }

    // Update an existing movie
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMovie(@PathVariable Long id, @RequestBody Movies updatedMovie) {
        Movies existingMovie = moviesService.getMovieById(id);
        if (existingMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }

        // Update all movie details
        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setDescription(updatedMovie.getDescription());
        existingMovie.setGenre(updatedMovie.getGenre());
        existingMovie.setDuration(updatedMovie.getDuration());
        existingMovie.setDirector(updatedMovie.getDirector());
        existingMovie.setCasting(updatedMovie.getCasting());
        existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
        existingMovie.setLanguagesAvailable(updatedMovie.getLanguagesAvailable());
        existingMovie.setRating(updatedMovie.getRating());
        existingMovie.setAvailable(updatedMovie.isAvailable());
        existingMovie.setDistributor(updatedMovie.getDistributor());
        existingMovie.setDimensionsAvailable(updatedMovie.getDimensionsAvailable());
        existingMovie.setVerticalPosterBASE64(updatedMovie.getVerticalPosterBASE64());
        existingMovie.setHorizontalPosterBASE64(updatedMovie.getHorizontalPosterBASE64());

        // Save the updated movie
        moviesService.saveMovie(existingMovie);
        return ResponseEntity.ok("Movie updated successfully.");
    }


    // Delete a movie by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        Movies movie = moviesService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }

        moviesService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully.");
    }
}
