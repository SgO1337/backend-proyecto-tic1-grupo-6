package com.example.demo.repository;


import com.example.demo.model.BookingScreenings;
import com.example.demo.model.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingScreeningRepository extends JpaRepository<BookingScreenings, Long> {

    @Query("SELECT Seats FROM BookingScreenings s WHERE s.idBookingScreening = :idBookingScreening")
    List<Seats> findAllBookingSeats(@Param("idBookingScreening") Long idBookingScreening);

    @Query("SELECT s FROM BookingScreenings s JOIN s.user u WHERE u.idUser = :idUser")
    List<BookingScreenings> getBookingScreeningByUserId(@Param("idUser") Long idUser);

}
