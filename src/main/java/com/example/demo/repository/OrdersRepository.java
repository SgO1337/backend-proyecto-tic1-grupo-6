package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT o FROM Orders o WHERE o.user.idUser = :userId")
    List<Orders> findByUserId(@Param("userId") Long userId);
}
