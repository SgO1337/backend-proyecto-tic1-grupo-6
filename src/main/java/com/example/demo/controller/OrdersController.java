package com.example.demo.controller;

import com.example.demo.model.Orders;
import com.example.demo.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.UserService;
import java.util.List;

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

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Orders order) {
        // Set default values if necessary
        if (order.getCancelled() == null) {
            order.setCancelled(false);
        }
        if (order.getDelivered() == null) {
            order.setDelivered(false);
        }

        if (!userService.existsById(order.getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user who supposedly placed the order does not exist"); // Return 404 Not Found pero no se por que no me deja que el response entity devuelva un tipo genérico
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
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Orders updatedOrder) {
        Orders order = ordersService.updateOrder(id, updatedOrder);

        if (!userService.existsById(updatedOrder.getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user who supposedly placed the order does not exist"); // Return 404 Not Found pero no se por que no me deja que el response entity devuelva un tipo genérico
        }

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
