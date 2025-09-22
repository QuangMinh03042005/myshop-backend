package com.minh.myshop.service.impl;

import com.minh.myshop.entity.OrderItem;
import com.minh.myshop.exception.NoSuchOrderException;
import com.minh.myshop.exception.ProductStockInvalid;
import com.minh.myshop.repository.OrderItemRepository;
import com.minh.myshop.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemRepository orderProductRepository;

    @Override
    public List<OrderItem> getAllByOrderOrderId(Integer id) {
        return orderProductRepository.findAllByOrderOrderId(id);
    }

    @Override
    public void deleteByOrderIdAndProductId(Integer orderId, Integer productId) {
        orderProductRepository.deleteByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public Optional<OrderItem> getByOrderIdAndProductId(Integer orderId, Integer productId) {
        return orderProductRepository.findByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public OrderItem addOrderProduct(OrderItem orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public void changeOrderProductQuantity(Integer orderId, Integer productId, Integer quantity) throws NoSuchOrderException, ProductStockInvalid {
        var op = getByOrderIdAndProductId(orderId, productId).orElseThrow(() -> new NoSuchOrderException("deo thay cai order nay"));
        if (op.getProduct().getQuantityInStock() < quantity) {
            throw new ProductStockInvalid("y la deo du hang de ban cho may!");
        }
        op.setQuantity(quantity);
        this.addOrderProduct(op);
    }
}
