package com.minh.myshop.service;

import com.minh.myshop.entity.Order;
import com.minh.myshop.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Order getById(Integer id);

    Integer getOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus);

    List<Integer> getAllOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus);

    Order getReferrerById(Integer id);

    void deleteOrder(Integer orderId);

    Order addOrder(Order order);
}
