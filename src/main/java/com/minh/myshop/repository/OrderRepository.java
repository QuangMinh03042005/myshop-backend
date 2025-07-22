package com.minh.myshop.repository;

import com.minh.myshop.entity.Order;

import com.minh.myshop.enums.OrderStatus;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Modifying
    @Query("DELETE FROM OrderProduct op WHERE op.order.id = :orderId")
    void deleteOrderProductsByOrderId(@Param("orderId") Integer orderId);

    @Query("SELECT o.orderId FROM Order o WHERE o.user.id = :userId and o.orderStatus = :orderStatus")
    Optional<Integer> findOrderIdByUserIdAndStatus(@Param("userId") Integer userId, @Param("orderStatus") OrderStatus orderStatus);

    @Query("SELECT o.orderId FROM Order o WHERE o.user.id = :userId and o.orderStatus = :orderStatus")
    List<Integer> findAllOrderIdByUserIdAndStatus(@Param("userId") Integer userId, @Param("orderStatus") OrderStatus orderStatus);
}
