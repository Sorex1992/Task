package com.example.task.service;

import com.example.task.dto.ProductDTO;
import com.example.task.model.Product;

import java.util.List;

public interface ProductService{
    List<Product> getAllProducts();

    Product createProduct(ProductDTO productDto);

    Product getProductById(Long productId);
}
