package com.mtxbjls.orders.services;

import com.mtxbjls.orders.model.dtos.BaseResponse;
import com.mtxbjls.orders.model.dtos.OrderItemRequest;
import com.mtxbjls.orders.model.dtos.OrderRequest;
import com.mtxbjls.orders.model.entities.Order;
import com.mtxbjls.orders.model.entities.OrderItem;
import com.mtxbjls.orders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){

        BaseResponse result = this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/v1/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if(result != null && !result.hasErrors()){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream().map((OrderItemRequest orderItemRequest) -> mapToOrderItems(orderItemRequest, order)).toList());
            this.orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Some of the products are not in stock");
        }

    }

    public OrderItem mapToOrderItems(OrderItemRequest orderItemRequest, Order order){
        return OrderItem.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }

}
