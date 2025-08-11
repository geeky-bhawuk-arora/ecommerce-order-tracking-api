package com.ecommerce.ordertracking.service.impl;

import com.ecommerce.ordertracking.dto.OrderItemRequest;
import com.ecommerce.ordertracking.dto.OrderRequest;
import com.ecommerce.ordertracking.dto.OrderResponse;
// import com.ecommerce.ordertracking.dto.OrderItemResponse;
import com.ecommerce.ordertracking.model.Order;
import com.ecommerce.ordertracking.model.OrderItem;
import com.ecommerce.ordertracking.repository.OrderRepository;
import com.ecommerce.ordertracking.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());

        // Map DTOs to entities and set the bidirectional relationship
        List<OrderItem> items = request.items().stream()
                .map(itemReq -> {
                    OrderItem item = new OrderItem();
                    item.setProductName(itemReq.productName());
                    item.setQuantity(itemReq.quantity());
                    item.setPrice(itemReq.price());
                    item.setOrder(order); // Link item back to the order
                    return item;
                })
                .collect(Collectors.toList());

        order.setItems(items);
        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        
        order.setStatus(status.toUpperCase());
        if ("DELIVERED".equalsIgnoreCase(status)) {
            order.setDeliveryDate(LocalDateTime.now());
        }
        
        Order updatedOrder = orderRepository.save(order);
        return mapToResponse(updatedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::mapToResponse) // Use a method reference for clean mapping
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // A private helper method to map an Order entity to an OrderResponse DTO
    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getId(), // I've corrected this to include the item's ID
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getStatus(),
                order.getOrderDate(),
                order.getDeliveryDate(),
                itemResponses
        );
    }
}