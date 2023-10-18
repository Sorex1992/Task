package com.example.task.service.impl;

import com.example.task.dto.ProductDTO;
import com.example.task.exception.NotFoundException;
import com.example.task.model.Product;
import com.example.task.repo.ProductRepository;
import com.example.task.service.ProductService;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Transactional
    public Product createProduct(ProductDTO productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();
        return productRepository.save(product);
    }
    @Transactional
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
    }


}
