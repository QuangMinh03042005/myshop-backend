package com.minh.myshop.service;

import com.minh.myshop.entity.OrderItem;
import com.minh.myshop.exception.NoSuchOrderException;
import com.minh.myshop.exception.ProductStockInvalid;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderItemService {

    List<OrderItem> getAllByOrderOrderId(Integer id);

    @Transactional
    void deleteByOrderIdAndProductId(Integer orderId, Integer productId);

    @Transactional
    Optional<OrderItem> getByOrderIdAndProductId(Integer orderId, Integer productId);

    @Transactional
    OrderItem addOrderProduct(OrderItem orderProduct);

    @Transactional
    void changeOrderProductQuantity(Integer orderId, Integer productId, Integer quantity) throws NoSuchOrderException, ProductStockInvalid;
}
