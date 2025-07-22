package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartProduct {
    @EmbeddedId
    CartProductId cartProductId = new CartProductId();

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    Cart cart;

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @JsonIgnore
    Product product;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "unit_price")
    Double unitPrice;

    @Column(name = "total_price", insertable = false, updatable = false)
    BigDecimal totalPrice;

    public CartProduct(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.unitPrice = this.product.getPrice();
        this.quantity = quantity;
        this.cartProductId = new CartProductId(this.cart.getCartId(), this.product.getProductId());
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
