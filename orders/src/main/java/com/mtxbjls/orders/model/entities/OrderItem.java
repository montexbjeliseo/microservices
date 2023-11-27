package com.mtxbjls.orders.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private Double price;
    private Long quantity;

    @ManyToOne
    @JoinColumn( name = "order_id")
    private Order order;
}
