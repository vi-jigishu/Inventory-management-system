package com.example.microservices.product_service.controller;

import com.example.microservices.product_service.dto.*;
import com.example.microservices.product_service.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public String testAllProducts() {
        return "all products";
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createProduct = ProductService.createProduct(productDTO);
        return ResponseEntity.ok(createProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updateProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updateProduct);
    }


    @PostMapping("/availability") 
    public ResponseEntity<ProductAvailabilityResponse> checkProductAvailability(@RequestBody List<ProductDTO> products) {
        ProductAvailabilityResponse availabilityList = productService.checkProductAvailability(products);
        ResponseEntity<ProductAvailabilityResponse> availabilityResponse = ResponseEntity.status(HttpStatus.OK).body(availabilityList);
        return availabilityResponse;
    }
}
