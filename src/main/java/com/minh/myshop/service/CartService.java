package com.minh.myshop.service;

import com.minh.myshop.entity.Cart;

public interface CartService {
    public Cart getCartByUserId(Integer userId);

    public Cart getCartById(Integer id);

    public Cart getReferrerById(Integer id);
}
