package com.minh.myshop.repository;

import com.minh.myshop.entity.CartItem;
import com.minh.myshop.entity.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    public List<CartItem> findAllByCart_cartId(@Param("cartId") Integer cartId);
}
