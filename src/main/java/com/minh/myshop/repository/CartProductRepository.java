package com.minh.myshop.repository;

import com.minh.myshop.entity.CartProduct;
import com.minh.myshop.entity.CartProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {
    public List<CartProduct> findAllByCart_cartId(@Param("cartId") Integer cartId);
}
