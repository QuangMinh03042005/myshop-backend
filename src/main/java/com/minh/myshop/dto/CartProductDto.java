package com.minh.myshop.dto;

import com.minh.myshop.entity.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDto {
    Integer cartId;
    Integer productId;
    String productName;
    String image;
    int quantity;
    int quantityInStock;
    Double unitPrice;
    BigDecimal totalPrice;

    public CartProductDto(CartProduct cartProduct) {
        this.cartId = cartProduct.getCart().getCartId();
        this.productId = cartProduct.getProduct().getProductId();
        this.productName = cartProduct.getProduct().getProductName();
        this.image = cartProduct.getProduct().getImage();
        this.quantity = cartProduct.getQuantity();
        this.quantityInStock = cartProduct.getProduct().getQuantityInStock();
        this.unitPrice = cartProduct.getUnitPrice();
        this.totalPrice = cartProduct.getTotalPrice();
    }
}
