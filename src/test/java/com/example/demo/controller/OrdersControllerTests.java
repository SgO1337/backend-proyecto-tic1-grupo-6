package com.example.demo.controller;

import com.example.demo.model.Orders;
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
import static org.mockito.Mockito.*;

public class OrdersControllerTests {

    @Mock
    private OrdersService ordersService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrdersController ordersController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ShouldCreateOrderSuccessfully() {
        Orders order = new Orders();
        order.setUserId(1L);

        when(userService.existsById(1L)).thenReturn(true);
        when(ordersService.saveOrder(order)).thenReturn(order);

        ResponseEntity<?> response = ordersController.createOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(ordersService, times(1)).saveOrder(order);
    }

    @Test
    void createOrder_ShouldReturnNotFoundIfUserDoesNotExist() {
        Orders order = new Orders();
        order.setUserId(2L);

        when(userService.existsById(2L)).thenReturn(false);

        ResponseEntity<?> response = ordersController.createOrder(order);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The user who supposedly placed the order does not exist", response.getBody());
        verify(ordersService, never()).saveOrder(any(Orders.class));
    }

    @Test
    void listOrders_ShouldReturnListOfOrders() {
        Orders order = new Orders();
        when(ordersService.getAllOrders()).thenReturn(Arrays.asList(order));

        ResponseEntity<List<Orders>> response = ordersController.listOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(ordersService, times(1)).getAllOrders();
    }

    @Test
    void getOrderById_ShouldReturnOrderIfExists() {
        Orders order = new Orders();
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
        Orders existingOrder = new Orders();
        existingOrder.setUserId(1L);
        Orders updatedOrder = new Orders();
        updatedOrder.setUserId(1L);

        when(userService.existsById(1L)).thenReturn(true);
        when(ordersService.updateOrder(1L, updatedOrder)).thenReturn(updatedOrder);

        ResponseEntity<?> response = ordersController.updateOrder(1L, updatedOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrder, response.getBody());
        verify(ordersService, times(1)).updateOrder(1L, updatedOrder);
    }

    @Test
    void updateOrder_ShouldReturnNotFoundIfUserDoesNotExist() {
        Orders updatedOrder = new Orders();
        updatedOrder.setUserId(2L);

        when(userService.existsById(2L)).thenReturn(false);

        ResponseEntity<?> response = ordersController.updateOrder(1L, updatedOrder);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The user who supposedly placed the order does not exist", response.getBody());
        verify(ordersService, never()).updateOrder(anyLong(), any(Orders.class));
    }

    @Test
    void updateOrder_ShouldReturnNotFoundIfOrderDoesNotExist() {
        Orders updatedOrder = new Orders();
        updatedOrder.setUserId(1L);

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
}
