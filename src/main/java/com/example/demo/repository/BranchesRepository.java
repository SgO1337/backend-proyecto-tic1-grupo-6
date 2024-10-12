package com.example.demo.repository;

import com.example.demo.model.Branches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchesRepository extends JpaRepository<Branches, Long> {

    @Query("SELECT DISTINCT r.branch FROM Screenings s JOIN s.room r WHERE s.movie.idMovie = :movieId")
    List<Branches> findBranchesByMovie(@Param("movieId") Long movieId);

}
