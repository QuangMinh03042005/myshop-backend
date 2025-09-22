package com.minh.myshop.service;

import com.minh.myshop.dto.OrderDto;
import com.minh.myshop.entity.Order;
import com.minh.myshop.enums.OrderStatus;
import com.minh.myshop.exception.ProductStockInvalid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Order getById(Integer id);

    Order getReferrer(Integer id);

    Order getOrderDetailById(Integer id);

    List<Order> getAllOrderDetail();

    List<Order> getAllOrderDetailByUserId(Integer userId);

    List<Order> getAllByUserId(Integer userId);

    Integer getOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus);

    List<Integer> getAllOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus);

    OrderDto addOrder(Order order);

    void deleteOrder(Integer orderId);

    void pay(OrderDto orderDto) throws ProductStockInvalid;

}
