package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class BookingScreenings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBookingScreening;

    //private LocalDateTime bookingTime; //no tiene sentido porque es el el mismo del screening, que es clave foranea de esta misma entidad

    private boolean isCancelled;

    // Corrected relationship with Seats, making it bidirectional
    @OneToMany(mappedBy = "bookingScreening")
    private List<Seats> seats;

    @OneToOne
    @JoinColumn(name = "idUser")
    private Users user;

    @OneToOne
    @JoinColumn(name = "idScreening")
    private Screenings screening;

    // Getters and setters
    public Long getIdBookingScreening() {
        return idBookingScreening;
    }

    public void setIdBookingScreening(Long idBookingScreening) {
        this.idBookingScreening = idBookingScreening;
    }
    /*
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }*/

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public List<Seats> getSeats() {
        return seats;
    }

    public void setSeats(List<Seats> seats) {
        this.seats = seats;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Screenings getScreening() {
        return screening;
    }

    public void setScreening(Screenings screening) {
        this.screening = screening;
    }
}
