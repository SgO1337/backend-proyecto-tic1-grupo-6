package com.example.demo.controller;

import com.example.demo.model.BookingScreenings;
import com.example.demo.model.Seats;
import com.example.demo.model.Users;
import com.example.demo.model.Screenings;
import com.example.demo.service.BookingScreeningService;
import com.example.demo.service.SeatsService;
import com.example.demo.service.UserService;
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

public class BookingScreeningsControllerTest {

    @Mock
    private BookingScreeningService bookingScreeningService;

    @Mock
    private SeatsService seatService;

    @Mock
    private UserService userService;

    @Mock
    private ScreeningService screeningService;

    @InjectMocks
    private BookingScreeningController bookingScreeningController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBookingScreening_ShouldReturnListOfBookingScreenings() {
        BookingScreenings bookingScreening = new BookingScreenings();
        when(bookingScreeningService.getAllBookingScreenings()).thenReturn(Arrays.asList(bookingScreening));

        List<BookingScreenings> result = bookingScreeningController.getBookingScreening();

        assertEquals(1, result.size());
        verify(bookingScreeningService, times(1)).getAllBookingScreenings();
    }

    @Test
    void getBookingScreeningById_ShouldReturnBookingScreeningIfFound() {
        BookingScreenings bookingScreening = new BookingScreenings();
        when(bookingScreeningService.getBookingScreeningById(1L)).thenReturn(bookingScreening);

        ResponseEntity<?> response = bookingScreeningController.getBookingScreeningById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookingScreening, response.getBody());
    }

    @Test
    void getBookingScreeningById_ShouldReturnNotFoundIfNotFound() {
        when(bookingScreeningService.getBookingScreeningById(1L)).thenReturn(null);

        ResponseEntity<?> response = bookingScreeningController.getBookingScreeningById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Booking Screening not found.", response.getBody());
    }

    @Test
    void createBookingScreening_ShouldCreateBookingScreeningSuccessfully() {
        BookingScreenings bookingScreening = new BookingScreenings();
        bookingScreening.setCancelled(false);
        bookingScreening.setSeats(Arrays.asList(new Seats(), new Seats(), new Seats()));

        // Set seat properties using setters
        bookingScreening.getSeats().get(0).setSeatRow(1);
        bookingScreening.getSeats().get(0).setSeatCol(1);
        bookingScreening.getSeats().get(1).setSeatRow(1);
        bookingScreening.getSeats().get(1).setSeatCol(2);
        bookingScreening.getSeats().get(2).setSeatRow(1);
        bookingScreening.getSeats().get(2).setSeatCol(3);

        Users user = new Users();
        user.setIdUser(2L);
        bookingScreening.setUser(user);

        Screenings screening = new Screenings();
        screening.setIdScreening(8L); // Set screening ID
        bookingScreening.setScreening(screening);

        when(userService.existsById(2L)).thenReturn(true);
        when(screeningService.existsById(8L)).thenReturn(true);

        // Mock seatService's findBySeatRowAndSeatColAndScreeningId method
        when(seatService.findBySeatRowAndSeatColAndScreeningId(anyInt(), anyInt(), eq(8L))).thenReturn(null);

        ResponseEntity<?> response = bookingScreeningController.createBookingScreening(bookingScreening);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Booking Screening created successfully.", response.getBody());

        // Verify that createAndBookSeat is called for each seat
        for (Seats seat : bookingScreening.getSeats()) {
            verify(seatService, times(1)).createAndBookSeat(seat);
        }
    }


    @Test
    void createBookingScreening_ShouldReturnBadRequestIfUserNotFound() {
        BookingScreenings bookingScreening = new BookingScreenings();
        bookingScreening.setUser(new Users()); // No ID set

        ResponseEntity<?> response = bookingScreeningController.createBookingScreening(bookingScreening);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found.", response.getBody());
    }

    @Test
    void createBookingScreening_ShouldReturnBadRequestIfScreeningNotFound() {
        BookingScreenings bookingScreening = new BookingScreenings();
        bookingScreening.setUser(new Users());
        bookingScreening.getUser().setIdUser(2L); // Set a valid user ID
        bookingScreening.setScreening(new Screenings());
        bookingScreening.getScreening().setIdScreening(8L); // Set a screening ID

        when(userService.existsById(2L)).thenReturn(true);
        when(screeningService.existsById(8L)).thenReturn(false); // Screening does not exist

        ResponseEntity<?> response = bookingScreeningController.createBookingScreening(bookingScreening);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Screening not found.", response.getBody());
    }

    @Test
    void deleteBookingScreeningById_ShouldDeleteBookingScreeningIfExists() {
        BookingScreenings bookingScreening = new BookingScreenings();
        bookingScreening.setIdBookingScreening(1L); // Set ID for the booking

        when(bookingScreeningService.getBookingScreeningById(1L)).thenReturn(bookingScreening);

        ResponseEntity<?> response = bookingScreeningController.deleteBookingScreeningById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Booking Screening and associated seats deleted successfully.", response.getBody());
        verify(seatService, times(1)).deleteSeatsByBookingScreening(1L);
        verify(bookingScreeningService, times(1)).deleteBookingScreeningById(1L);
    }

    @Test
    void deleteBookingScreeningById_ShouldReturnNotFoundIfBookingScreeningDoesNotExist() {
        when(bookingScreeningService.getBookingScreeningById(1L)).thenReturn(null);

        ResponseEntity<?> response = bookingScreeningController.deleteBookingScreeningById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Booking Screening not found.", response.getBody());
    }

    @Test
    void createBookingScreening_ShouldReturnConflictIfDuplicateSeats() {
        BookingScreenings bookingScreening = new BookingScreenings();
        bookingScreening.setCancelled(false);
        bookingScreening.setSeats(Arrays.asList(new Seats(), new Seats()));

        // Set seat properties using setters
        bookingScreening.getSeats().get(0).setSeatRow(1);
        bookingScreening.getSeats().get(0).setSeatCol(1);
        bookingScreening.getSeats().get(1).setSeatRow(1);
        bookingScreening.getSeats().get(1).setSeatCol(1); // Duplicate seat

        Users user = new Users();
        user.setIdUser(2L);
        bookingScreening.setUser(user);

        Screenings screening = new Screenings();
        screening.setIdScreening(8L); // Set screening ID
        bookingScreening.setScreening(screening);

        when(userService.existsById(2L)).thenReturn(true);
        when(screeningService.existsById(8L)).thenReturn(true);

        // Mocking the seatService to return a booked seat for the first seat
        when(seatService.findBySeatRowAndSeatColAndScreeningId(1, 1, 8L)).thenReturn(new Seats());

        ResponseEntity<?> response = bookingScreeningController.createBookingScreening(bookingScreening);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Seat (1, 1) is already booked for this screening.", response.getBody());
    }

}
