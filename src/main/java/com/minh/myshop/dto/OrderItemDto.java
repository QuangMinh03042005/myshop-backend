package com.minh.myshop.dto;

import com.minh.myshop.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Integer orderId;
    private Integer productId;
    private String productName;
    private int quantity;
    private Double unitPrice;
    private BigDecimal totalPrice;

    public OrderItemDto(OrderItem orderItem) {
        this.orderId = orderItem.getOrder().getOrderId();
        this.quantity = orderItem.getQuantity();
        this.setProductId(orderItem.getProduct().getProductId());
        this.setProductName(orderItem.getProduct().getProductName());
        this.setUnitPrice(orderItem.getUnitPrice());
        this.setTotalPrice(orderItem.getTotalPrice());
    }
}
