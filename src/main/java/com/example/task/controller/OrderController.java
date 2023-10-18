package com.example.task.controller;

import com.example.task.dto.OrderDTO;
import com.example.task.model.Order;
import com.example.task.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllOrders() {
        return mapToListDTO(orderService.getAllOrders());
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDto) {
        return mapToDTO(orderService.createOrder(orderDto));
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable Long orderId) {
        return mapToDTO(orderService.getOrderById(orderId));
    }

    @PutMapping("/{orderId}/pay")
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderDTO payOrder(@PathVariable Long orderId) {
        return mapToDTO(orderService.payOrder(orderId));
    }

    private List<OrderDTO> mapToListDTO(List<Order> orders) {
        return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
    }
    private OrderDTO mapToDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}
