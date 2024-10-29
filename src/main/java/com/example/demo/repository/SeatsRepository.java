package com.example.demo.repository;
import com.example.demo.model.Screenings;
import com.example.demo.model.Seats;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatsRepository extends JpaRepository<Seats, Long> {
    // devuelve asientos OCUPADOS por idScreening (todos los asientos)
    @Query("SELECT s FROM Seats s WHERE s.bookingScreening.screening.idScreening = :idScreening")
    List<Seats> getAllBookedSeatsByidScreening(@Param("idScreening") Long idScreening);

    // Query to find all seats by screening ID
    @Query("SELECT s FROM Seats s WHERE s.bookingScreening.screening.idScreening = :screeningId")
    List<Seats> findByScreeningId(@Param("screeningId") Long screeningId);

    //para borrar seats por id bs
    @Modifying
    @Transactional
    @Query("DELETE FROM Seats s WHERE s.bookingScreening.idBookingScreening = :bookingScreeningId")
    void deleteByBookingScreeningId(@Param("bookingScreeningId") Long bookingScreeningId);

    // Query to find a seat by row, column, and screening ID
    @Query("SELECT s FROM Seats s WHERE s.seatRow = :seatRow AND s.seatCol = :seatCol AND s.bookingScreening.screening.idScreening = :screeningId")
    Seats findBySeatRowAndSeatColAndScreeningId(@Param("seatRow") int seatRow, @Param("seatCol") int seatCol, @Param("screeningId") Long screeningId);
}
