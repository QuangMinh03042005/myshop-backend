package com.minh.myshop.service;

import com.minh.myshop.entity.Cart;

public interface CartService {
    public Cart getCartByUserId(Integer userId);

    public Cart getById(Integer id);

    public Cart getReferrerById(Integer id);
}
