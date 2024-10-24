package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private Integer CI;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Integer age;

    private String role; //admin, usuario, root, etc

    //@OneToMany()
    //@JoinColumn(name= "idBookingScreenings", nullable = false)
    //private List<BookingScreenings> bookingScreenings;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Integer getCI() {
        return CI;
    }

    public void setCI(Integer CI) {
        this.CI = CI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
/*
    public List<BookingScreenings> getBookingScreenings() {
        return bookingScreenings;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBookingScreenings(List<BookingScreenings> bookingScreenings) {
        this.bookingScreenings = bookingScreenings;
    }*/

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(long l) {
        this.idUser = l;
    }
}
