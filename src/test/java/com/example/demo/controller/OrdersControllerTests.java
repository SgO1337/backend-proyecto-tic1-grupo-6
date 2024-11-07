package com.example.demo.controller;

import com.example.demo.model.Orders;
import com.example.demo.model.Users;
import com.example.demo.service.OrdersService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrdersControllerTests {

    @Mock
    private OrdersService ordersService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrdersController ordersController;

    private Orders order;
    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new Users();
        user.setIdUser(1L);

        order = new Orders();
        order.setUser(user);
        order.setCancelled(false);
        order.setDelivered(false);
    }

    @Test
    void createOrder_ShouldCreateOrderSuccessfully() {
        when(userService.existsById(1L)).thenReturn(true);
        when(ordersService.saveOrder(order)).thenReturn(order);

        ResponseEntity<?> response = ordersController.createOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(ordersService, times(1)).saveOrder(order);
    }

    @Test
    void createOrder_ShouldReturnNotFoundIfUserDoesNotExist() {
        when(userService.existsById(1L)).thenReturn(false);

        ResponseEntity<?> response = ordersController.createOrder(order);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The user who supposedly placed the order does not exist", response.getBody());
        verify(ordersService, never()).saveOrder(any(Orders.class));
    }

    @Test
    void listOrders_ShouldReturnListOfOrders() {
        when(ordersService.getAllOrders()).thenReturn(Arrays.asList(order));

        ResponseEntity<List<Orders>> response = ordersController.listOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(ordersService, times(1)).getAllOrders();
    }

    @Test
    void getOrderById_ShouldReturnOrderIfExists() {
        when(ordersService.getOrderById(1L)).thenReturn(order);

        ResponseEntity<Orders> response = ordersController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    void getOrderById_ShouldReturnNotFoundIfOrderDoesNotExist() {
        when(ordersService.getOrderById(1L)).thenReturn(null);

        ResponseEntity<Orders> response = ordersController.getOrderById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateOrder_ShouldUpdateOrderSuccessfully() {
        Orders updatedOrder = new Orders();
        updatedOrder.setUser(user);

        when(userService.existsById(1L)).thenReturn(true);
        when(ordersService.updateOrder(1L, updatedOrder)).thenReturn(updatedOrder);

        ResponseEntity<?> response = ordersController.updateOrder(1L, updatedOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrder, response.getBody());
        verify(ordersService, times(1)).updateOrder(1L, updatedOrder);
    }

    @Test
    void updateOrder_ShouldReturnNotFoundIfOrderDoesNotExist() {
        Orders updatedOrder = new Orders();
        updatedOrder.setUser(user);

        when(userService.existsById(1L)).thenReturn(true);
        when(ordersService.updateOrder(1L, updatedOrder)).thenReturn(null);

        ResponseEntity<?> response = ordersController.updateOrder(1L, updatedOrder);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ordersService, times(1)).updateOrder(1L, updatedOrder);
    }

    @Test
    void deleteOrder_ShouldDeleteOrderSuccessfully() {
        doNothing().when(ordersService).deleteOrder(1L);

        ResponseEntity<Void> response = ordersController.deleteOrder(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ordersService, times(1)).deleteOrder(1L);
    }

    @Test
    void getOrdersByUserId_ShouldReturnOrdersForUser() {
        when(ordersService.getOrdersByUserId(1L)).thenReturn(Arrays.asList(order));

        List<Orders> response = ordersController.getOrdersByUserId(1L);

        assertEquals(1, response.size());
        assertEquals(order, response.get(0));
        verify(ordersService, times(1)).getOrdersByUserId(1L);
    }
}