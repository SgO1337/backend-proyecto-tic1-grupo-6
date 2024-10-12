package com.example.demo.service;

import com.example.demo.model.Branches;
import com.example.demo.model.Movies;
import com.example.demo.repository.BranchesRepository;
import com.example.demo.repository.MoviesRepository;
import com.example.demo.repository.ScreeningRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {


    @Autowired
    private final MoviesRepository moviesRepository;

    @Autowired
    private final BranchesRepository branchRepository;
    @Autowired
    private BranchesRepository branchesRepository;

    @Autowired
    private ScreeningRepository screeningRepository;


    public boolean existsById(Long id) {
        return moviesRepository.existsById(id);
    }



    public MoviesService(MoviesRepository moviesRepository, BranchesRepository branchesRepository, ScreeningRepository screeningRepository) {
        this.moviesRepository = moviesRepository;
        this.branchRepository = branchesRepository;
        this.screeningRepository = screeningRepository;
    }
    // get all movies
    public  List<Movies> getAllMovies() {
        return moviesRepository.findAll();
    }
    // get movie by ID
    public Movies getMovieById(Long id) {
        return moviesRepository.findById(id).orElse(null);
    }
    // save movie
    @Transactional
    public Movies saveMovie(Movies movie) {
        return moviesRepository.save(movie);
    }
    // delete movie
    @Transactional
    public void deleteMovie(Long id) {
        //Check existence of the movie before deletion
        Optional<Movies> movie = moviesRepository.findById(id);
        movie.ifPresent(m -> moviesRepository.deleteById(id));
    }

    @Transactional
    public List<Movies> getAllAvailableMovies() {
        List<Movies> moviesAvailable =  moviesRepository.getAllAvailableMovies();
        return moviesAvailable;
    }


    // Logic to fetch branches where the movie is being screened
    public List<Branches> getBranchesForMovie(Long movieId) {
        return branchRepository.findBranchesByMovie(movieId);
    }

    // Logic to fetch screening dates for the given movie at the given branch
    public List<String> getScreeningDatesForMovieAtBranch(Long movieId, Long branchId) {
        return screeningRepository.findScreeningDatesByMovieAndBranch(movieId, branchId);
    }

    // Logic to fetch screening times for the given movie, branch, and date
    public List<String> getScreeningTimesForMovieBranchAndDate(Long movieId, Long branchId, String date) {
        return screeningRepository.findScreeningTimesByMovieBranchAndDate(movieId, branchId, date);
    }
}




