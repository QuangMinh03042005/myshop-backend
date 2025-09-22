package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minh.myshop.dto.CartItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {

    @EmbeddedId
    OrderItemId orderItemId = new OrderItemId();

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Order order;

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @JsonIgnore
    Product product;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "unit_price")
    Double unitPrice;

    @Column(name = "total_price", updatable = false, insertable = false)
    BigDecimal totalPrice;

    public OrderItem(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.unitPrice = this.product.getPrice();
        this.quantity = quantity;
        this.orderItemId = new OrderItemId(order.getOrderId(), product.getProductId());
    }

    public OrderItem(CartItemDto cartProductDto, Order order, Integer orderId, Product product) {
        this.order = order;
        this.product = product;
        this.orderItemId = new OrderItemId(orderId, cartProductDto.getProductId());
        this.quantity = cartProductDto.getQuantity();
        this.unitPrice = cartProductDto.getUnitPrice();
        this.totalPrice = cartProductDto.getTotalPrice();
    }

    @Override
    public String toString() {
        return "{" +
//                " orderId=" + order.getOrderId() +
//                ", productId=" + product.getProductId() +
                " productName=" + product.getProductName() +
                ", quantity=" + quantity +
                '}';
    }
}
