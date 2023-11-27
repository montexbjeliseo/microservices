package com.mtxbjls.orders.model.dtos;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderRequest {
    private List<OrderItemRequest> orderItems;
}
