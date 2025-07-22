package com.minh.myshop.dto;

import com.minh.myshop.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto {

    Integer productId;
    String productName;
    int quantity;

    public OrderProductDto(OrderProduct orderProduct) {
        this.productId = orderProduct.getProduct().getProductId();
        this.productName = orderProduct.getProduct().getProductName();
        this.quantity = orderProduct.getQuantity();
    }
}
