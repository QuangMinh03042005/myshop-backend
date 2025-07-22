package com.minh.myshop.repository;

import com.minh.myshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    //    @Query(value = "select * from carts where user_id = :userId LIMIT 1", nativeQuery = true)
    public Optional<Cart> findCartByUser_userId(@Param("userId") Integer userId);

    public Optional<Cart> findReferrerByCartId(Integer cartId);
}
