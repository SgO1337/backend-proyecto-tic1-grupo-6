package com.example.demo.repository;
import com.example.demo.model.Screenings;
import com.example.demo.model.Seats;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;//no se si estabien

public interface SeatsRepository extends JpaRepository<Seats, Long> {
    // devuelve asientos OCUPADOS por idScreening

    @Query("SELECT s FROM Seats s WHERE s.bookingScreening.screening.idScreening = :idScreening")
    List<Seats> getAllBookedSeatsByidScreening(@Param("idScreening") Long idScreening);
    /*
    // devuelve asientos DESOCUPADOS por idScreening
    @Query("SELECT s FROM Seats s WHERE s.isBooked = false AND s.screening.idScreening = :idScreening")
    List<Seats> getAllUnbookedSeatsByidScreening(@Param("idScreening") Long idScreening);

    // Find seat by seatId and idScreening (esto sirve para luego ocuparlos/desocuparlos)
    @Query("SELECT s FROM Seats s WHERE s.idSeat = :seatId AND s.screening.idScreening = :idScreening")
    Optional<Seats> findSeatByidScreeningAndSeatId(@Param("idScreening") Long idScreening, @Param("seatId") Long seatId);*/

    //find screening by id
    @Query("SELECT s FROM Screenings s WHERE s.idScreening = :idScreening")
    Screenings getScreeningById(Long idScreening);

    @Query("SELECT s FROM Seats s WHERE s.seatRow = :seatRow AND s.seatCol = :seatCol")
    Seats findBySeatRowAndSeatCol(@Param("seatRow") int seatRow, @Param("seatCol") int seatCol);

    // Query to find a seat by row, column, and screening ID
    //@Query("SELECT s FROM Seats s WHERE s.seatRow = :seatRow AND s.seatCol = :seatCol AND s.bookingScreening.screening.idScreening = :screeningId")
    //Seats findBySeatRowAndSeatColAndScreeningId(@Param("seatRow") int seatRow, @Param("seatCol") int seatCol, @Param("screeningId") Long screeningId);

    // Query to find all seats by screening ID
    @Query("SELECT s FROM Seats s WHERE s.bookingScreening.screening.idScreening = :screeningId")
    List<Seats> findByScreeningId(@Param("screeningId") Long screeningId);

    ///
    @Modifying
    @Transactional
    @Query("DELETE FROM Seats s WHERE s.bookingScreening.idBookingScreening = :bookingScreeningId")
    void deleteByBookingScreeningId(@Param("bookingScreeningId") Long bookingScreeningId);

    // Query to find a seat by row, column, and screening ID
    @Query("SELECT s FROM Seats s WHERE s.seatRow = :seatRow AND s.seatCol = :seatCol AND s.bookingScreening.screening.idScreening = :screeningId")
    Seats findBySeatRowAndSeatColAndScreeningId(@Param("seatRow") int seatRow, @Param("seatCol") int seatCol, @Param("screeningId") Long screeningId);

}
