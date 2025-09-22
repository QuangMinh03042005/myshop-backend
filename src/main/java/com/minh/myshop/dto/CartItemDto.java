package com.minh.myshop.dto;

import com.minh.myshop.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Integer cartId;
    private Integer productId;
    private String productName;
    private String image;
    private int quantity;
    private int quantityInStock;
    private Double unitPrice;
    private BigDecimal totalPrice;

    public CartItemDto(CartItem cartProduct) {
        this.setCartId(cartProduct.getCart().getCartId());
        this.setProductId(cartProduct.getProduct().getProductId());
        this.setProductName(cartProduct.getProduct().getProductName());
        this.setImage(cartProduct.getProduct().getImage());
        this.setQuantity(cartProduct.getQuantity());
        this.setQuantityInStock(cartProduct.getProduct().getQuantityInStock());
        this.setUnitPrice(cartProduct.getUnitPrice());
        this.setTotalPrice(cartProduct.getTotalPrice());
    }
}
