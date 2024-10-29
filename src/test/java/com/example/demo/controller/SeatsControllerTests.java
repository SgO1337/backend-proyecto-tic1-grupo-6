package com.example.demo.controller;

import com.example.demo.model.Seats;
import com.example.demo.service.SeatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SeatsControllerTests {

    private MockMvc mockMvc;

    @Mock
    private SeatsService seatService;

    @InjectMocks
    private SeatsController seatsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(seatsController).build();
    }

    @Test
    void getAllSeats_ShouldReturnListOfSeats() {
        Long idScreening = 1L;
        List<Seats> seatsList = new ArrayList<>();
        Seats seat = new Seats();
        seat.setIdSeat(1L);
        seat.setSeatRow(1);
        seat.setSeatCol(1);
        seatsList.add(seat);

        when(seatService.getAllSeatsByidScreening(idScreening)).thenReturn(seatsList);

        ResponseEntity<List<Seats>> response = seatsController.getAllSeats(idScreening);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seatsList, response.getBody());
        verify(seatService, times(1)).getAllSeatsByidScreening(idScreening);
    }

    @Test
    void getAllSeats_ShouldReturnEmptyListIfNoSeatsFound() {
        Long idScreening = 1L;
        when(seatService.getAllSeatsByidScreening(idScreening)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Seats>> response = seatsController.getAllSeats(idScreening);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(seatService, times(1)).getAllSeatsByidScreening(idScreening);
    }

    @Test
    void getBookedSeats_ShouldReturnEmptyListIfNoBookedSeatsFound() {
        Long idScreening = 1L;
        when(seatService.getSeatsByScreeningId(idScreening)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Seats>> response = seatsController.getBookedSeats(idScreening);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(seatService, times(1)).getSeatsByScreeningId(idScreening);
    }
}
