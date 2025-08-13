package com.minh.myshop.service;

import com.minh.myshop.entity.CartProduct;
import com.minh.myshop.entity.CartProductId;

import java.util.List;

public interface CartProductService {

    CartProduct getById(CartProductId cartProductId);

    CartProduct getReferrerById(CartProductId cartProductId);

    CartProduct addCartProduct(CartProduct cartProduct);

    List<CartProduct> getAllProductByCartId(Integer cartId);

    boolean existById(CartProductId cartProductId);

}
