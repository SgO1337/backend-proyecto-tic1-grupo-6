package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Branches{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBranch;
    private String location;
    
    public Long getIdBranch(){
        return this.idBranch;
    }
    public void setIdBranch(Long idBranch){
        this.idBranch = idBranch;
    }
    public String getLocation(){
        return this.location;
    }
    public void setLocation(String location){
        this.location = location;
    }
}