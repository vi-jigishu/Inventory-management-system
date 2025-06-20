package com.example.microservices.product_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAvilabilityResponse implements Serializable{
    private static final long serialVersionUID = 1l;
    List<ProductAvailability> productAvailabilityList;
}
