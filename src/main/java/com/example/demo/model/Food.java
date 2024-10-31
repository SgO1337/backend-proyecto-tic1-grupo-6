package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFood;

    private String name;
    private String type;
    private double price;

    @Column(columnDefinition = "TEXT")
    private String imageBASE64;

    public String getImageBASE64() {
        return imageBASE64;
    }

    public void setImageBASE64(String imageBASE64) {
        this.imageBASE64 = imageBASE64;
    }

    public Long getIdFood() {
        return idFood;
    }

    public void setIdFood(Long idFood) {
        this.idFood = idFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
