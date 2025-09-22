package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
    @EmbeddedId
    CartItemId cartItemId = new CartItemId();

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

    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.unitPrice = this.product.getPrice();
        this.quantity = quantity;
        this.cartItemId = new CartItemId(this.cart.getCartId(), this.product.getProductId());
    }
}
