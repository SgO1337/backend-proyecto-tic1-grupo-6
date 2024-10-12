package com.example.demo.repository;

import com.example.demo.model.Movies;
import com.example.demo.model.Rooms;
import com.example.demo.model.Screenings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screenings, Long> {
    List<Screenings> findByMovieAndRoom(Movies movie, Rooms room);

    @Query("SELECT DISTINCT s.date FROM Screenings s " + // Changed to s.date
            "JOIN s.room r " +
            "WHERE s.movie.idMovie = :movieId " +
            "AND r.branch.idBranch = :branchId")
    List<String> findScreeningDatesByMovieAndBranch(@Param("movieId") Long movieId, @Param("branchId") Long branchId);

    @Query("SELECT DISTINCT s.time FROM Screenings s " +  // Fetch the time as string
            "JOIN s.room r " +
            "WHERE s.movie.idMovie = :movieId " +
            "AND r.branch.idBranch = :branchId " +
            "AND s.date = :date")  // Filter by date
    List<String> findScreeningTimesByMovieBranchAndDate(@Param("movieId") Long movieId,
                                                        @Param("branchId") Long branchId,
                                                        @Param("date") String date);

    @Query("SELECT s.idScreening FROM Screenings s " +  // Assuming 'idScreening' is the unique identifier
            "JOIN s.room r " +
            "WHERE s.movie.idMovie = :movieId " +
            "AND s.date = :date " +
            "AND s.time = :time " +  // Ensure you have a 'time' field in your Screening entity
            "AND r.branch.idBranch = :branchId")
    Long findScreeningIdByMovieBranchDateAndTime(@Param("movieId") Long movieId,
                                                 @Param("date") String date,
                                                 @Param("time") String time,
                                                 @Param("branchId") Long branchId);
}