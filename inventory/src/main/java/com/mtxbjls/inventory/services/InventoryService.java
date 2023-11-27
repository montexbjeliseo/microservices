package com.mtxbjls.inventory.services;

import com.mtxbjls.inventory.model.dtos.BaseResponse;
import com.mtxbjls.inventory.model.dtos.OrderItemRequest;
import com.mtxbjls.inventory.model.entities.Inventory;
import com.mtxbjls.inventory.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    public boolean isInStock(String sku) {
        return  this.inventoryRepository
                .findBySku(sku)
                .filter(value -> value.getQuantity() > 0)
                .isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> items) {
        var errorList = new ArrayList<String>();
        List<String> skus = items.stream().map(OrderItemRequest::getSku).toList();
        List<Inventory> inventoryList = this.inventoryRepository.findBySkuIn(skus);

        items.forEach(item -> {
            var itemInventory = inventoryList.stream().filter(value -> value.getSku().equals(item.getSku())).findFirst();
            if(itemInventory.isEmpty()){
                errorList.add(String.format("Product with sku %s does not exists", item.getSku()));
            } else if(itemInventory.get().getQuantity() < item.getQuantity()){
                errorList.add(String.format("Product with sku %s has insufficient quantity", item.getSku()));
            }
        });
        return errorList.isEmpty() ? new BaseResponse(null) : new BaseResponse(errorList.toArray(new String[0]));
    }
}
