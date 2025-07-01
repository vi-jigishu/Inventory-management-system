package com.example.microservices.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservices.product_service.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<product, Long> {
    Optional<Product> findBySkuCode(String skuCode); 
}
