package com.example.demo.controller;

import com.example.demo.model.Orders;
import com.example.demo.service.OrdersService;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrdersController is a REST controller that manages order-related operations.
 * It provides endpoints for creating, listing, viewing, updating, and deleting orders.
 */
@RestController
@RequestMapping("/api/orders")
//@CrossOrigin(origins = "http://localhost:3000")
public class OrdersController {

    private final OrdersService ordersService;
    private final UserService userService;

    public OrdersController(OrdersService ordersService, UserService userService) {
        this.ordersService = ordersService;
        this.userService = userService;
    }

    /**
     * Creates a new order.
     *
     * @param order the order details to create
     * @return a ResponseEntity containing the created order or an error message
     */
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Orders order) {
        if (order.getCancelled() == null) {
            order.setCancelled(false);
        }
        if (order.getDelivered() == null) {
            order.setDelivered(false);
        }

        if (!userService.existsById(order.getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user who supposedly placed the order does not exist");
        }

        Orders createdOrder = ordersService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Gets a list of all orders.
     *
     * @return a ResponseEntity containing a list of orders
     */
    @GetMapping
    public ResponseEntity<List<Orders>> listOrders() {
        List<Orders> ordersList = ordersService.getAllOrders();
        return ResponseEntity.ok(ordersList);
    }

    /**
     * Gets a specific order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return a ResponseEntity containing the order or a 404 Not Found response
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = ordersService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    /**
     * Updates an existing order.
     *
     * @param id the ID of the order to update
     * @param updatedOrder the updated order details
     * @return a ResponseEntity containing the updated order or an error message
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Orders updatedOrder) {
        if (!userService.existsById(updatedOrder.getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user who supposedly placed the order does not exist");
        }

        Orders order = ordersService.updateOrder(id, updatedOrder);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete
     * @return a ResponseEntity with no content on success
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}