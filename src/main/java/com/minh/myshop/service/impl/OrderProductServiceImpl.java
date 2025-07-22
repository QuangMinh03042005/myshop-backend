package com.minh.myshop.service.impl;

import com.minh.myshop.entity.OrderProduct;
import com.minh.myshop.exception.NoSuchOrderException;
import com.minh.myshop.exception.ProductStockInvalid;
import com.minh.myshop.repository.OrderProductRepository;
import com.minh.myshop.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    @Autowired
    OrderProductRepository orderProductRepository;

    @Override
    public List<OrderProduct> findAllByOrderOrderId(Integer id) {
        return orderProductRepository.findAllByOrderOrderId(id);
    }

    @Override
    public void deleteByOrderIdAndProductId(Integer orderId, Integer productId) {
        orderProductRepository.deleteByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public Optional<OrderProduct> findByOrderIdAndProductId(Integer orderId, Integer productId) {
        return orderProductRepository.findByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public void save(OrderProduct orderProduct) {
        orderProductRepository.save(orderProduct);
    }

    @Override
    public void changeOrderProductQuantity(Integer orderId, Integer productId, Integer quantity) throws NoSuchOrderException, ProductStockInvalid {
        var op = findByOrderIdAndProductId(orderId, productId).orElseThrow(() -> new NoSuchOrderException("deo thay cai order nay"));
        if (op.getProduct().getQuantityInStock() < quantity) {
            throw new ProductStockInvalid("y la deo du hang de ban cho may!");
        }
        op.setQuantity(quantity);
        save(op);
    }
}
