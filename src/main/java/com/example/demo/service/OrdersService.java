package com.example.demo.service;

import com.example.demo.model.Orders;
import com.example.demo.model.OrderFood;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.repository.OrderFoodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderFoodRepository orderFoodRepository;

    public OrdersService(OrdersRepository ordersRepository, OrderFoodRepository orderFoodRepository) {
        this.ordersRepository = ordersRepository;
        this.orderFoodRepository = orderFoodRepository;
    }

    // Save a new order
    @Transactional
    public Orders saveOrder(Orders order) {
        // Save the order first
        Orders savedOrder = ordersRepository.save(order);

        // Save each OrderFood item linked to the order
        for (OrderFood orderFood : order.getOrderFood()) {
            orderFoodRepository.save(orderFood);
        }

        return savedOrder;
    }

    // Get a list of all orders
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    // Get a specific order by ID
    public Orders getOrderById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    // Update an order
    @Transactional
    public Orders updateOrder(Long id, Orders updatedOrder) {
        Orders existingOrder = getOrderById(id);
        if (existingOrder == null) {
            return null;
        }

        // Update the order fields
        existingOrder.setCancelled(updatedOrder.getCancelled());
        existingOrder.setDelivered(updatedOrder.getDelivered());
        existingOrder.setDate(updatedOrder.getDate());

        // Optionally update orderFood items here as well
        // Note: If orderFood items are updated, they must be handled separately

        return ordersRepository.save(existingOrder);
    }

    // Delete an order
    @Transactional
    public void deleteOrder(Long id) {
        Orders order = getOrderById(id);
        if (order != null) {
            // Delete associated orderFood entries
            for (OrderFood orderFood : order.getOrderFood()) {
                orderFoodRepository.delete(orderFood);
            }
            // Delete the order
            ordersRepository.delete(order);
        }
    }
}
