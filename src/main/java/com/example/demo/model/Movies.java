package com.example.demo.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;
import com.example.demo.model.Screenings;
import org.springframework.cglib.core.Local;

@Entity
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovie;

    private String title;
    private String description;
    private String genre;

    // Change to integer to represent duration in minutes
    private int duration;

    private String director;
    private String cast;

    // Use LocalDate for better date management
    private LocalDate releaseDate;

    @ElementCollection
    private List<String> languagesAvailable;

    private String rating;
    private boolean isAvailable;
    private String distributor;

    private String dimension;

    private String verticalPosterBASE64;
    private String horizontalPosterBASE64;

    @OneToMany(mappedBy = "movie")
    private List<Screenings> screenings; // Changed to singular to match the convention

    public Long getIdMovie() {
        return idMovie;
    }

    //Getters y Setters
    public void setIdMovie(Long idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getLanguagesAvailable() {
        return languagesAvailable;
    }

    public void setLanguagesAvailable(List<String> languagesAvailable) {
        this.languagesAvailable = languagesAvailable;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getVerticalPosterBASE64() {
        return verticalPosterBASE64;
    }

    public void setVerticalPosterBASE64(String verticalPosterBASE64) {
        this.verticalPosterBASE64 = verticalPosterBASE64;
    }

    public String getHorizontalPosterBASE64() {
        return horizontalPosterBASE64;
    }

    public void setHorizontalPosterBASE64(String horizontalPosterBASE64) {
        this.horizontalPosterBASE64 = horizontalPosterBASE64;
    }

    public List<Screenings> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screenings> screenings) {
        this.screenings = screenings;
    }
}
