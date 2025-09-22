package com.minh.myshop.controller;

import com.minh.myshop.dto.OrderDto;
import com.minh.myshop.exception.ProductStockInvalid;
import com.minh.myshop.mapper.OrderItemMapper;
import com.minh.myshop.mapper.OrderMapper;
import com.minh.myshop.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Lazy
@RequestMapping("/api/users/{userId}/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @GetMapping
    ResponseEntity<?> getOrder(@PathVariable Integer userId) {
        var orders = orderService.getAllOrderDetailByUserId(userId);
        return ResponseEntity.ok(orders.stream().map((order) -> {
            var orderDto = orderMapper.toDto(order);
            orderDto.setUserId(userId);
            orderDto.setItems(order.getItems().stream().map((orderItem) -> {
                var orderItemDto = orderItemMapper.toDto(orderItem);
                orderItemDto.setOrderId(orderItem.getOrder().getOrderId());
                orderItemDto.setProductId(orderItem.getProduct().getProductId());
                orderItemDto.setProductName(orderItem.getProduct().getProductName());
                return orderItemDto;
            }).toList());
            return orderDto;
        }).toList());
    }

    @PostMapping
    ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) throws ProductStockInvalid {
        orderService.pay(orderDto);
        return ResponseEntity.ok().build();
    }
}
