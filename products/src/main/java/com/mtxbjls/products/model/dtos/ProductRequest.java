package com.mtxbjls.products.model.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
    private String sku;
    private String name;
    private String description;
    private double price;
    private boolean status;
}
