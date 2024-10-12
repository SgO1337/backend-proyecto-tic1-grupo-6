package com.example.demo.controller;

import com.example.demo.model.Orders;
import com.example.demo.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
//@CrossOrigin(origins = "http://localhost:3000")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {
        // Set default values if necessary
        if (order.getCancelled() == null) {
            order.setCancelled(false);
        }
        if (order.getDelivered() == null) {
            order.setDelivered(false);
        }

        Orders createdOrder = ordersService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }


    // Get a list of all orders
    @GetMapping
    public ResponseEntity<List<Orders>> listOrders() {
        List<Orders> ordersList = ordersService.getAllOrders();
        return ResponseEntity.ok(ordersList);
    }

    // Get a specific order by its ID
    @GetMapping("/view/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = ordersService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    // Update an existing order
    @PutMapping("/update/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders updatedOrder) {
        Orders order = ordersService.updateOrder(id, updatedOrder);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    // Delete an order
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
