package com.example.microservices.product_service.entity;

import org.hibernate.annotations.ValueGenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    

    @Id
    @GeneratedValue(strategy = @ValueGenerationType.IDENTITY)
    private Long id;
    private String name;
    private String skuCode;
    private String description;
    private int quantity;
    private  double price;
}
