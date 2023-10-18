package com.example.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItemDTO {
    private Long id;
    private int quantity;
    private OrderDTO order;
    private ProductDTO product;
}
