package com.minh.myshop.service.impl;

import com.minh.myshop.entity.Cart;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.repository.CartRepository;
import com.minh.myshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserServiceImpl userService;

    @Override
    public Cart getCartByUserId(Integer userId) {
        var user = userService.getById(userId);
        var cart = cartRepository.findCartByUser_userId(userId).orElse(null);
        // đảm bảo rằng mỗi user sẽ luôn có một giỏ hàng
        if (cart == null) {
            cart = Cart.builder().user(user).build();
            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public Cart getById(Integer id) {
        return cartRepository.findById(id).orElseThrow(() -> new NotFoundException("cart not found with id = " + id));
    }

    @Override
    public Cart getReferrerById(Integer id) {
        return cartRepository.getReferenceById(id);
    }
}
