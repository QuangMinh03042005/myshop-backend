package com.minh.myshop.service;

import com.minh.myshop.dto.CartItemDto;
import com.minh.myshop.entity.CartItem;
import com.minh.myshop.entity.CartItemId;
import com.minh.myshop.exception.OutOfQuantityInStock;

import java.util.List;

public interface CartItemService {

    CartItem getById(CartItemId cartProductId);

    CartItem getReferrer(CartItemId cartProductId);

    CartItemDto addCartItem(CartItem cartItem);

    CartItemDto addCartItem(CartItemDto cartItemDto) throws OutOfQuantityInStock;

    CartItemDto updateCartItem(CartItemDto cartItemDto) throws OutOfQuantityInStock;

    void deleteCartItem(CartItemId cartItemId);

    List<CartItemDto> getAllItemByCartId(Integer cartId);

    List<CartItemDto> getAllItemByUserId(Integer userId);

    boolean existById(CartItemId cartProductId);

}
