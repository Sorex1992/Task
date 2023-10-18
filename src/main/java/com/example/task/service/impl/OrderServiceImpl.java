package com.example.task.service.impl;

import com.example.task.dto.OrderDTO;
import com.example.task.exception.ConflictException;
import com.example.task.exception.NotFoundException;
import com.example.task.model.LineItem;
import com.example.task.model.Order;
import com.example.task.model.Product;
import com.example.task.model.User;
import com.example.task.repo.LineItemRepository;
import com.example.task.repo.OrderRepository;
import com.example.task.repo.ProductRepository;
import com.example.task.repo.UserRepository;
import com.example.task.service.OrderService;
import com.example.task.service.ProductService;
import com.example.task.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);

        if (order.getUser() == null || order.getLineItems() == null || order.getLineItems().isEmpty()) {
            throw new ConflictException("Not enough information");
        }

        // Здесь используем кастомный запрос с JOIN FETCH для загрузки всех связанных данных
        Order createdOrder = orderRepository.findByPaidFalseAndCreatedAtBefore(order.getId())
                .orElseThrow(() -> new NotFoundException("Order not found"));

        return createdOrder;
    }


    @Transactional
    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Transactional
    @Override
    public Order payOrder(Long orderId) {
        Order order = getOrderById(orderId);
        if (order.isPaid()) {
            throw new IllegalStateException("Order already paid");
        }
        order.setPaid(true);
        return orderRepository.save(order);
    }

    @Transactional
    @Scheduled(fixedRate = 600000)
    public void deleteUnpaidOrders() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<Order> unpaidOrders = orderRepository.findByPaidFalseAndCreatedAtBefore(tenMinutesAgo);
        orderRepository.deleteAll(unpaidOrders);
    }
}
