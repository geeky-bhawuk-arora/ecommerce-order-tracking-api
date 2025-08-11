package com.ecommerce.ordertracking.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    Long id;
    String customerName;
    String status;
    LocalDateTime orderDate;
    LocalDateTime deliveryDate;
    List<OrderItemRequest> items;
}
