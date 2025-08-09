package com.minh.myshop.service;

import com.minh.myshop.entity.CartProduct;
import com.minh.myshop.entity.CartProductId;

import java.util.List;

public interface CartProductService {

    CartProduct getById(CartProductId cartProductId);

    List<CartProduct> getAllProductByCartId(Integer cartId);

    CartProduct getReferrerById(CartProductId cartProductId);

    boolean existById(CartProductId cartProductId);
}
