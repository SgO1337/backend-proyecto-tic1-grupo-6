package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeat;

    private boolean isBooked;
    private int seatRow;
    private int seatCol;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screenings screening;

    @ManyToOne
    @JoinColumn(name = "booking_screening_id")
    @JsonIgnore //esto resuelve el problema de la recursion infinita, mostrando solo los asientos asignados cuando muestro un bs
    private BookingScreenings bookingScreening;

    // Getters and setters
    public Long getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(Long idSeat) {
        this.idSeat = idSeat;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(int seatCol) {
        this.seatCol = seatCol;
    }

    public Screenings getScreening() {
        return screening;
    }

    public void setScreening(Screenings screening) {
        this.screening = screening;
    }

    public BookingScreenings getBookingScreening() {
        return bookingScreening;
    }

    public void setBookingScreening(BookingScreenings bookingScreening) {
        this.bookingScreening = bookingScreening;
    }
}
