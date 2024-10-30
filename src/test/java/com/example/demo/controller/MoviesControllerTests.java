package com.example.demo.controller;
import com.example.demo.model.Branches;
import com.example.demo.model.Movies;
import com.example.demo.service.MoviesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

    public class MoviesControllerTests {

        @Mock
        private MoviesService moviesService;

        @InjectMocks
        private MoviesController moviesController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void listMovies_ShouldReturnListOfMovies() {
            Movies movie = new Movies();
            when(moviesService.getAllMovies()).thenReturn(Arrays.asList(movie));

            ResponseEntity<List<Movies>> response = moviesController.listMovies();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().size());
            verify(moviesService, times(1)).getAllMovies();
        }

        @Test
        void listAvailableMovies_ShouldReturnListOfAvailableMovies() {
            Movies movie = new Movies();
            when(moviesService.getAllAvailableMovies()).thenReturn(Arrays.asList(movie));

            ResponseEntity<List<Movies>> response = moviesController.listAvailableMovies();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().size());
            verify(moviesService, times(1)).getAllAvailableMovies();
        }

        @Test
        void viewMovie_ShouldReturnMovieIfFound() {
            Movies movie = new Movies();
            when(moviesService.getMovieById(1L)).thenReturn(movie);

            ResponseEntity<?> response = moviesController.viewMovie(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(movie, response.getBody());
        }

        @Test
        void viewMovie_ShouldReturnNotFoundIfMovieNotFound() {
            when(moviesService.getMovieById(1L)).thenReturn(null);

            ResponseEntity<?> response = moviesController.viewMovie(1L);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Movie not found.", response.getBody());
        }

        @Test
        void createMovie_ShouldCreateMovieSuccessfully() {
            Movies movie = new Movies();
            movie.setTitle("Sample Movie");
            movie.setDescription("Sample Description");
            movie.setGenre("Action");
            movie.setDuration(120);
            movie.setDirector("Sample Director");
            movie.setCasting("Actor A, Actor B");
            movie.setReleaseDate(LocalDate.now());
            movie.setLanguagesAvailable(Arrays.asList("English", "Spanish"));
            movie.setRating("PG-13");
            movie.setDistributor("Sample Distributor");
            movie.setDimensionsAvailable(Arrays.asList("2D", "3D"));
            movie.setVerticalPosterBASE64("sampleVerticalPosterBase64String");
            movie.setHorizontalPosterBASE64("sampleHorizontalPosterBase64String");

            when(moviesService.saveMovie(movie)).thenReturn(movie);

            ResponseEntity<String> response = moviesController.createMovie(movie);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals("Movie created successfully.", response.getBody());
            verify(moviesService, times(1)).saveMovie(movie);
        }


        @Test
        void createMovie_ShouldReturnBadRequestIfDataIncomplete() {
            Movies movie = new Movies(); // No title or description

            ResponseEntity<String> response = moviesController.createMovie(movie);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Incomplete set of data.", response.getBody());
            verify(moviesService, never()).saveMovie(any(Movies.class));
        }

        @Test
        void updateMovie_ShouldUpdateMovieSuccessfully() {
            Movies existingMovie = new Movies();
            existingMovie.setIdMovie(1L);
            existingMovie.setTitle("Old Title");

            Movies updatedMovie = new Movies();
            updatedMovie.setTitle("New Title");

            when(moviesService.getMovieById(1L)).thenReturn(existingMovie);

            ResponseEntity<String> response = moviesController.updateMovie(1L, updatedMovie);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Movie updated successfully.", response.getBody());
            assertEquals("New Title", existingMovie.getTitle());
            verify(moviesService, times(1)).saveMovie(existingMovie);
        }

        @Test
        void updateMovie_ShouldReturnNotFoundIfMovieNotFound() {
            Movies updatedMovie = new Movies();
            updatedMovie.setTitle("New Title");

            when(moviesService.getMovieById(1L)).thenReturn(null);

            ResponseEntity<String> response = moviesController.updateMovie(1L, updatedMovie);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Movie not found.", response.getBody());
            verify(moviesService, never()).saveMovie(any(Movies.class));
        }

        @Test
        void deleteMovie_ShouldDeleteMovieIfExists() {
            Movies movie = new Movies();
            when(moviesService.getMovieById(1L)).thenReturn(movie);

            ResponseEntity<String> response = moviesController.deleteMovie(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Movie deleted successfully.", response.getBody());
            verify(moviesService, times(1)).deleteMovie(1L);
        }

        @Test
        void deleteMovie_ShouldReturnNotFoundIfMovieNotFound() {
            when(moviesService.getMovieById(1L)).thenReturn(null);

            ResponseEntity<String> response = moviesController.deleteMovie(1L);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Movie not found.", response.getBody());
            verify(moviesService, never()).deleteMovie(1L);
        }

        @Test
        void getBranchesForMovie_ShouldReturnBranchesIfFound() {
            Branches branch = new Branches();
            branch.setIdBranch(1L);
            branch.setLocation("Main Street");

            when(moviesService.getBranchesForMovie(1L)).thenReturn(Arrays.asList(branch));

            ResponseEntity<?> response = moviesController.getBranchesForMovie(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, ((List<?>) response.getBody()).size());
        }

        @Test
        void getBranchesForMovie_ShouldReturnNotFoundIfNoBranchesFound() {
            when(moviesService.getBranchesForMovie(1L)).thenReturn(Collections.emptyList());

            ResponseEntity<?> response = moviesController.getBranchesForMovie(1L);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("No branches were found where the movie is being screened.", response.getBody());
        }

        @Test
        void getScreeningDatesForMovieAtBranch_ShouldReturnScreeningDatesIfFound() {
            List<String> screeningDates = Arrays.asList("2024-11-01", "2024-11-02");
            when(moviesService.getScreeningDatesForMovieAtBranch(1L, 1L)).thenReturn(screeningDates);

            ResponseEntity<List<String>> response = moviesController.getScreeningDatesForMovieAtBranch(1L, 1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(screeningDates, response.getBody());
        }

        @Test
        void getScreeningDatesForMovieAtBranch_ShouldReturnNotFoundIfNoDatesFound() {
            when(moviesService.getScreeningDatesForMovieAtBranch(1L, 1L)).thenReturn(Collections.emptyList());

            ResponseEntity<List<String>> response = moviesController.getScreeningDatesForMovieAtBranch(1L, 1L);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(Collections.emptyList(), response.getBody());
        }

        @Test
        void getScreeningTimesForMovieBranchAndDate_ShouldReturnScreeningTimesIfFound() {
            List<String> screeningTimes = Arrays.asList("10:00 AM", "02:00 PM");
            when(moviesService.getScreeningTimesForMovieBranchAndDate(1L, 1L, "2024-11-01")).thenReturn(screeningTimes);

            ResponseEntity<List<String>> response = moviesController.getScreeningTimesForMovieBranchAndDate(1L, 1L, "2024-11-01");

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(screeningTimes, response.getBody());
        }

        @Test
        void getScreeningTimesForMovieBranchAndDate_ShouldReturnNotFoundIfNoTimesFound() {
            when(moviesService.getScreeningTimesForMovieBranchAndDate(1L, 1L, "2024-11-01")).thenReturn(Collections.emptyList());

            ResponseEntity<List<String>> response = moviesController.getScreeningTimesForMovieBranchAndDate(1L, 1L, "2024-11-01");

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(Collections.emptyList(), response.getBody());
        }

       /* @Test
        void getScreeningId_ShouldReturnScreeningId() {
            MoviesController.ScreeningRequestDTO request = new MoviesController.ScreeningRequestDTO();
            request.setMovieId(1L);
            request.setBranchId(1L);
            request.setDate("2024-11-01");
            request.setTime("10:00 AM");

            when(moviesService.getScreeningId(1L, "2024-11-01", "10:00 AM", 1L, 1L)).thenReturn(123L);

            ResponseEntity<MoviesController.ScreeningIdResponseDTO> response = moviesController.getScreeningId(request);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(123L, response.getBody().getScreeningId());
        }*/
    }


