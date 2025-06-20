package com.example.ordermanagemnet.order_service.client;

import java.net.http.HttpHeaders;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceClient.class);

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private string productServiceUrl;

    public ProductServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductAvailability> checkProductAvailability(List<OrderItemDto> products) {
        String checkAvailabilityUrl = productServiceUrl + "/availability";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<OrderItemDto>> requestEntity = new HttpEntity<>(products, headers);

        try {
            ResponseEntity<ProductAvailabilityResponse> respone = restTemplate.exchange(
                checkAvailabilityUrl,
                HttpMethod.POST,
                requestEntity,
                ProductAvailabilityResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody().getProductAvailibilityList();
            } else {
                logger.error("Failed to check product availability. Status code: {}", response.getStatusCode());
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Exception occured while checking product availability: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
