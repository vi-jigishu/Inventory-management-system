package com.example.microservices.product_service.service;

import org.springframework.stereotype.Service;

import com.example.microservices.product_service.dto.*;
import com.example.microservices.product_service.entity.*;
import com.example.microservices.product_service.exception.*;
import com.example.microservices.product_service.repository.*;

import lombok.extern.slf4j.Slf4j;




@Service
@Slf4j
public class ProductService {
    

    ProductRepository productRepositoy;

    public ProductService(ProuctRepository productRepositoy) {
        this.productRepositoy = productRepositoy;
    }

    
    // public ProductDTO createProduct(ProductDTO productDTO) {
    //     Product Product = convertToEntity(productDTO);
    //     product savedProduct
    // }
}
