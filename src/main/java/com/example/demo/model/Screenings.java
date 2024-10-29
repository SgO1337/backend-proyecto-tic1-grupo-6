package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Screenings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idScreening;
    private String language;
    private String date;
    private String time;
    private String subtitles;

    @ManyToOne()
    @JoinColumn(name = "idMovie", nullable = false)
    private Movies movie;

    @ManyToOne // Change to ManyToOne
    @JoinColumn(name = "idRoom", nullable = false, referencedColumnName = "idRoom")
    private Rooms room;

    public Long getIdScreening() {
        return idScreening;
    }

    public void setIdScreening(Long idScreening) {
        this.idScreening = idScreening;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    public Rooms getRoom() {
        return this.room;
    }

    public void setRoom(Rooms room) {
        this.room = room;
    }
}
