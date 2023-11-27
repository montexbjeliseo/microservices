package com.mtxbjls.inventory.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private Long quantity;
}
