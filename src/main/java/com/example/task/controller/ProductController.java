package com.example.task.controller;

import com.example.task.dto.ProductDTO;
import com.example.task.model.Product;
import com.example.task.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return mapToListDTO(productService.getAllProducts());
    }

    @PostMapping
//    @PreAuthorize("hasRole('MANAGER')")
    public ProductDTO createProduct(@RequestBody ProductDTO productDto) {
        return mapToDTO(productService.createProduct(productDto));
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductById(@PathVariable Long productId) {
        return mapToDTO(productService.getProductById(productId));
    }
    private List<ProductDTO> mapToListDTO(List<Product> products) {
        return products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
    }
    private ProductDTO mapToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
