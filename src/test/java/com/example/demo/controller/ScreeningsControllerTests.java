package com.example.demo.controller;

import com.example.demo.model.Screenings;
import com.example.demo.service.MoviesService;
import com.example.demo.service.RoomsService;
import com.example.demo.service.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScreeningsControllerTests {

    @Mock
    private ScreeningService screeningService;

    @Mock
    private MoviesService movieService;

    @Mock
    private RoomsService roomService;

    @InjectMocks
    private ScreeningsController screeningsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listScreenings_ShouldReturnListOfScreenings() {
        Screenings screening = new Screenings();
        when(screeningService.getAllScreenings()).thenReturn(Arrays.asList(screening));

        ResponseEntity<List<Screenings>> response = screeningsController.listScreenings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(screeningService, times(1)).getAllScreenings();
    }

    @Test
    void viewScreening_ShouldReturnScreeningIfFound() {
        Screenings screening = new Screenings();
        when(screeningService.getScreeningById(1L)).thenReturn(screening);

        ResponseEntity<?> response = screeningsController.viewScreening(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(screening, response.getBody());
    }

    @Test
    void viewScreening_ShouldReturnNotFoundIfScreeningDoesNotExist() {
        when(screeningService.getScreeningById(1L)).thenReturn(null);

        ResponseEntity<?> response = screeningsController.viewScreening(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Screening not found.", response.getBody());
    }

    @Test
    void createScreening_ShouldCreateScreeningSuccessfully() {
        Screenings screening = new Screenings();
        screening.setMovie(new MoviesService());
        screening.setRoom(new RoomsService());

        when(roomService.existsById(screening.getRoom().getIdRoom())).thenReturn(true);
        when(movieService.existsById(screening.getMovie().getIdMovie())).thenReturn(true);
        when(screeningService.createScreening(screening)).thenReturn(screening);

        ResponseEntity<?> response = screeningsController.createScreening(screening);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(screening, response.getBody());
        verify(screeningService, times(1)).createScreening(screening);
    }

    @Test
    void createScreening_ShouldReturnNotFoundIfRoomDoesNotExist() {
        Screenings screening = new Screenings();
        screening.setRoom(new RoomsService());

        when(roomService.existsById(screening.getRoom().getIdRoom())).thenReturn(false);

        ResponseEntity<?> response = screeningsController.createScreening(screening);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The requested room wasn't found.", response.getBody());
        verify(screeningService, never()).createScreening(any(Screenings.class));
    }

    @Test
    void createScreening_ShouldReturnNotFoundIfMovieDoesNotExist() {
        Screenings screening = new Screenings();
        screening.setMovie(new MoviesService());
        screening.setRoom(new RoomsService());

        when(roomService.existsById(screening.getRoom().getIdRoom())).thenReturn(true);
        when(movieService.existsById(screening.getMovie().getIdMovie())).thenReturn(false);

        ResponseEntity<?> response = screeningsController.createScreening(screening);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The requested movie wasn't found.", response.getBody());
        verify(screeningService, never()).createScreening(any(Screenings.class));
    }

    @Test
    void updateScreening_ShouldUpdateScreeningSuccessfully() {
        Screenings existingScreening = new Screenings();
        existingScreening.setIdScreening(1L);

        Screenings updatedScreening = new Screenings();
        updatedScreening.setIdScreening(1L);

        when(screeningService.getScreeningById(1L)).thenReturn(existingScreening);
        when(movieService.existsById(updatedScreening.getMovie().getIdMovie())).thenReturn(true);
        when(roomService.existsById(updatedScreening.getRoom().getIdRoom())).thenReturn(true);
        when(screeningService.saveScreening(existingScreening)).thenReturn(updatedScreening);

        ResponseEntity<?> response = screeningsController.updateScreening(1L, updatedScreening);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedScreening, response.getBody());
        verify(screeningService, times(1)).saveScreening(existingScreening);
    }

    @Test
    void updateScreening_ShouldReturnNotFoundIfScreeningDoesNotExist() {
        Screenings updatedScreening = new Screenings();
        updatedScreening.setMovie(new MoviesService());
        updatedScreening.setRoom(new RoomsService());

        when(screeningService.getScreeningById(1L)).thenReturn(null);

        ResponseEntity<?> response = screeningsController.updateScreening(1L, updatedScreening);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Screening not found.", response.getBody());
    }

    @Test
    void deleteScreening_ShouldDeleteScreeningIfExists() {
        Screenings screening = new Screenings();
        when(screeningService.getScreeningById(1L)).thenReturn(screening);

        ResponseEntity<String> response = screeningsController.deleteScreening(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Screening deleted successfully.", response.getBody());
        verify(screeningService, times(1)).deleteScreening(1L);
    }

    @Test
    void deleteScreening_ShouldReturnNotFoundIfScreeningDoesNotExist() {
        when(screeningService.getScreeningById(1L)).thenReturn(null);

        ResponseEntity<String> response = screeningsController.deleteScreening(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Screening not found.", response.getBody());
    }
}
