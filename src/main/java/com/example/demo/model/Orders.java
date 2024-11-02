package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser") // Ensure this matches your Users entity
    private Users user;

    private Boolean cancelled;

    private Boolean delivered;

    private Date date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id") // This will create a foreign key in OrderFood
    private List<OrderFood> orderFood;

    // Default constructor
    public Orders() {
        this.cancelled = false; // Default to false
        this.delivered = false; // Default to false
    }

    // Getters and Setters
    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderFood> getOrderFood() {
        return orderFood;
    }

    public void setOrderFood(List<OrderFood> orderFood) {
        this.orderFood = orderFood;
    }
}
