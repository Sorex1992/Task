package com.example.task.repo;

import com.example.task.model.LineItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
}
