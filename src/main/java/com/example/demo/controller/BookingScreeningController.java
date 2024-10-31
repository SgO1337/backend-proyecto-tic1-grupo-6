package com.example.demo.controller;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import com.example.demo.model.BookingScreenings;
import com.example.demo.model.Seats;
import com.example.demo.service.BookingScreeningService;
import com.example.demo.service.SeatsService;
import com.example.demo.service.ScreeningService;

import java.util.List;
@RestController
@RequestMapping("/api/booking-screening")
public class BookingScreeningController {

    private final BookingScreeningService bookingScreeningService;
    private final SeatsService seatService;
    private final UserService userService;
    private final ScreeningService screeningService;

    public BookingScreeningController(BookingScreeningService bookingScreeningService, SeatsService seatService, UserService userService, ScreeningService screeningService) {
        this.bookingScreeningService = bookingScreeningService;
        this.seatService = seatService;
        this.userService = userService;
        this.screeningService = screeningService;
    }

    /**
     * Obtiene la lista completa de reservas de screenings.
     * @return Lista de todas las reservas de screenings.
     */
    @GetMapping
    public List<BookingScreenings> getBookingScreening() {
        return bookingScreeningService.getAllBookingScreenings();
    }

    /**
     * Busca y obtiene una reserva de screening por su ID.
     * @param id ID de la reserva de screening a buscar.
     * @return ResponseEntity con la reserva si se encuentra o un mensaje de error si no existe.
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<?> getBookingScreeningById(@PathVariable Long id) {
        BookingScreenings bookingScreening = bookingScreeningService.getBookingScreeningById(id);
        if (bookingScreening == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking Screening not found.");
        }
        return ResponseEntity.ok(bookingScreening);
    }

    /**
     * Crea una nueva reserva de screening si el usuario y el screening existen y si los asientos no están reservados.
     * @param bookingScreenings Objeto BookingScreenings con la información de la reserva.
     * @return ResponseEntity con mensaje de éxito si la creación es exitosa o mensaje de error en caso contrario.
     */
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> createBookingScreening(@RequestBody BookingScreenings bookingScreenings) {
        if (!userService.existsById(bookingScreenings.getUser().getIdUser())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        if (!screeningService.existsById(bookingScreenings.getScreening().getIdScreening())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Screening not found.");
        }

        List<Seats> seats = bookingScreenings.getSeats();

        // Check if any of the requested seats are already booked for this screening
        if (seats != null && !seats.isEmpty()) {
            for (Seats seat : seats) {
                Seats bookedSeat = seatService.findBySeatRowAndSeatColAndScreeningId(
                        seat.getSeatRow(),
                        seat.getSeatCol(),
                        bookingScreenings.getScreening().getIdScreening()
                );

                if (bookedSeat != null) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Seat (" + seat.getSeatRow() + ", " + seat.getSeatCol() + ") is already booked for this screening.");
                }
            }
        }

        // Save the booking screening first
        BookingScreenings savedBookingScreening = bookingScreeningService.saveBookingScreening(bookingScreenings);

        // Assign the saved booking screening to the seats
        if (seats != null && !seats.isEmpty()) {
            for (Seats seat : seats) {
                seat.setBookingScreening(savedBookingScreening); // Set the bookingScreening reference
                seatService.createAndBookSeat(seat); // Save the updated seat
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Booking Screening created successfully.");
    }

    /**
     * Elimina una reserva de screening y todos los asientos asociados.
     * @param id ID de la reserva de screening a eliminar.
     * @return ResponseEntity con mensaje de éxito si la eliminación es exitosa o mensaje de error si la reserva no existe.
     */
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteBookingScreeningById(@PathVariable Long id) {
        BookingScreenings bookingScreening = bookingScreeningService.getBookingScreeningById(id);
        if (bookingScreening == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking Screening not found.");
        }

        // Delete all seats related to this booking screening using the seatService
        seatService.deleteSeatsByBookingScreening(bookingScreening.getIdBookingScreening());

        // Delete the booking screening itself
        bookingScreeningService.deleteBookingScreeningById(id);

        return ResponseEntity.ok("Booking Screening and associated seats deleted successfully.");
    }

    //NO SE PUEDE MODIFICAR UN BOOKINGSCREENING PORQUE SERIA MUY COMPLICADO, PREGUNTARLE A ROLO SI PRETENDE AGREGARLO
}
