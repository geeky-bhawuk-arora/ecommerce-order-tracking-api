package com.ecommerce.ordertracking.service;

import com.ecommerce.ordertracking.dto.OrderResponse;
import com.ecommerce.ordertracking.dto.OrderRequest;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse updateOrderStatus(Long orderId, String status);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getOrdersByStatus(String status);
    List<OrderResponse> getAllOrders();
}
