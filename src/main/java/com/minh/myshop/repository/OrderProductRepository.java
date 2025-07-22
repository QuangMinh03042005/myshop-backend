package com.minh.myshop.repository;

import com.minh.myshop.entity.OrderProduct;
import com.minh.myshop.entity.OrderProductId;
import com.minh.myshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    @Transactional
    List<OrderProduct> findAllByOrderOrderId(Integer orderId);

    @Transactional
    @Modifying
    // @Query(value = "delete from order_product where order_id = :orderId")
    @Query(value = "delete from OrderProduct op where op.order.id = :orderId")
    int deleteAllByOrderId(@Param("orderId") Integer orderId);

    @Transactional
    @Modifying
    @Query(value = "delete from order_product where order_id = :orderId and product_id = :productId", nativeQuery = true)
    void deleteByOrderIdAndProductId(@Param("orderId") Integer orderId, @Param("productId") Integer productId);

    @Transactional
    @Query(value = "select * from order_product where order_id = :orderId and product_id = :productId", nativeQuery = true)
    Optional<OrderProduct> findByOrderIdAndProductId(@Param("orderId") Integer orderId, @Param("productId") Integer productId);

}
