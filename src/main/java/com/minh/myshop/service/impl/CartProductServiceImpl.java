package com.minh.myshop.service.impl;

import com.minh.myshop.entity.CartProduct;
import com.minh.myshop.entity.CartProductId;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.repository.CartProductRepository;
import com.minh.myshop.service.CartProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartProductServiceImpl implements CartProductService {

    private final CartProductRepository cartProductRepository;

    @Override
    public CartProduct getById(CartProductId cartProductId) {
        return cartProductRepository.findById(cartProductId).orElseThrow(() -> new NotFoundException("cartId = " + cartProductId.getCartId() + " or productId = " + cartProductId.getProductId() + " not found"));
    }

    @Override
    public List<CartProduct> getAllProductByCartId(Integer cartId) {
        return cartProductRepository.findAllByCart_cartId(cartId);
    }

    @Override
    public CartProduct getReferrerById(CartProductId cartProductId) {
        CartProduct o;
        try {
            o = cartProductRepository.getReferenceById(cartProductId);
        } catch (Exception e) {
            throw new NotFoundException("cartId = " + cartProductId.getCartId() + " or productId = " + cartProductId.getProductId() + " not found");
        }
        return o;
    }
}
