package com.example.task.service;

import com.example.task.dto.OrderDTO;
import com.example.task.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order createOrder(OrderDTO orderDTO);

    Order getOrderById(Long orderId);

    Order payOrder(Long orderId);
}
