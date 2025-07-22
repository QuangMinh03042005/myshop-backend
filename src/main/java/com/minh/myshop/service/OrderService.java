package com.minh.myshop.service;

import com.minh.myshop.entity.Order;
import com.minh.myshop.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    Order getOrderById(Integer id);

    Integer findOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus);

    List<Integer> findAllOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus);

    Order getReferrerById(Integer id);

    void deleteOrder(Integer orderId);

    void save(Order order);
}
