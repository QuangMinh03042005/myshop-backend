package com.minh.myshop.service.impl;

import com.minh.myshop.entity.Order;
import com.minh.myshop.enums.OrderStatus;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.repository.OrderRepository;
import com.minh.myshop.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order getById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("order not found with id = " + id));
    }

    @Override
    public Integer getOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus) {
        return orderRepository.findOrderIdByUserIdAndStatus(userId, orderStatus).orElseThrow(() -> new NotFoundException("can't find orderId with userId = " + userId + " or orderStatus = " + orderStatus));
    }

    @Override
    public List<Integer> getAllOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus) {
        return orderRepository.findAllOrderIdByUserIdAndStatus(userId, orderStatus);
    }

    @Override
    public Order getReferrerById(Integer id) {
        return orderRepository.getReferenceById(id);
    }

    @Transactional
    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteOrderProductsByOrderId(orderId); // Delete from order_product
        orderRepository.deleteById(orderId); // Now delete the order
    }

    @Transactional
    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }


}
