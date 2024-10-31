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

    private int seatRow;
    private int seatCol;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idBookingScreening")
    private BookingScreenings bookingScreening;

    // Getters and setters
    public Long getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(Long idSeat) {
        this.idSeat = idSeat;
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

    public BookingScreenings getBookingScreening() {
        return bookingScreening;
    }

    public void setBookingScreening(BookingScreenings bookingScreening) {
        this.bookingScreening = bookingScreening;
    }
}
