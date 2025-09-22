package com.minh.myshop.service.impl;

import com.minh.myshop.dto.OrderDto;
import com.minh.myshop.entity.CartItem;
import com.minh.myshop.entity.CartItemId;
import com.minh.myshop.entity.Order;
import com.minh.myshop.entity.OrderItem;
import com.minh.myshop.enums.OrderStatus;
import com.minh.myshop.enums.PaymentMethod;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.exception.ProductStockInvalid;
import com.minh.myshop.mapper.CartItemMapper;
import com.minh.myshop.mapper.OrderItemMapper;
import com.minh.myshop.mapper.OrderMapper;
import com.minh.myshop.repository.CartItemRepository;
import com.minh.myshop.repository.OrderItemRepository;
import com.minh.myshop.repository.OrderRepository;
import com.minh.myshop.repository.UserRepository;
import com.minh.myshop.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final CartItemMapper cartItemMapper;

    private final ProductServiceImpl productService;
    private final CartServiceImpl cartService;

    @Override
    public Order getById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("order not found with id = " + id));
    }

    @Override
    public Order getReferrer(Integer id) {
        return orderRepository.getReferenceById(id);
    }

    @Override
    public Order getOrderDetailById(Integer id) {
        return orderRepository.findByIdWithOrderItemProductAndUser(id).orElseThrow(() -> new NotFoundException("order not found with id = " + id));
    }

    @Override
    public List<Order> getAllOrderDetail() {
        return orderRepository.findAllWithOrderItemProductAndUser();
    }

    @Override
    public List<Order> getAllOrderDetailByUserId(Integer userId) {
        return orderRepository.findAllByUserIdWithOrderItemProductAndUser(userId);
    }

    @Override
    public List<Order> getAllByUserId(Integer userId) {
        return orderRepository.findAllByUser_userId(userId);
    }

    @Override
    public Integer getOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus) {
        return orderRepository.findOrderIdByUserIdAndStatus(userId, orderStatus).orElseThrow(() -> new NotFoundException("can't find orderId with userId = " + userId + " or orderStatus = " + orderStatus));
    }

    @Override
    public List<Integer> getAllOrderIdByUserIdAndStatus(Integer userId, OrderStatus orderStatus) {
        return orderRepository.findAllOrderIdByUserIdAndStatus(userId, orderStatus);
    }


    @Transactional
    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteOrderProductsByOrderId(orderId); // Delete from order_product
        orderRepository.deleteById(orderId); // Now delete the order
    }

    @Transactional
    @Override
    public OrderDto addOrder(Order order) {
        return orderMapper.toDto(orderRepository.saveAndFlush(order));
    }

    @Override
    @Transactional
    public void pay(OrderDto orderDto) throws ProductStockInvalid {
        var order = orderMapper.toEntity(orderDto);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUser(userRepository.getReferenceById(orderDto.getUserId()));
        var cartId = cartService.getCartByUserId(orderDto.getUserId()).getCartId();
        BigDecimal totalAmount = BigDecimal.valueOf(0);
        if (order.getPaymentMethod().equals(PaymentMethod.COD)) {
            var savedOrder = this.addOrder(order);
            var orderId = savedOrder.getOrderId();
            productService.changeListProductStock(orderDto.getItems());
            var orderItems = new ArrayList<OrderItem>();
            var cartItems = new ArrayList<CartItem>();
            for (var e : orderDto.getItems()) {
                OrderItem orderItem = orderItemMapper.toEntity(e);
                orderItem.setOrder(this.getReferrer(orderId));
                orderItem.setProduct(productService.getReferrer(e.getProductId()));
                orderItems.add(orderItem);
                var cartItem = cartItemMapper.toEntity(orderItem);
                cartItem.setCartItemId(new CartItemId(cartId, e.getProductId()));
                cartItem.setCart(cartService.getReferrer(cartId));
                cartItem.setProduct(productService.getReferrer(e.getProductId()));
                cartItems.add(cartItem);
                totalAmount = totalAmount.add(e.getTotalPrice());
            }
            orderItemRepository.saveAll(orderItems);
            cartItemRepository.deleteAll(cartItems);
            order.setTotalAmount(totalAmount);
            this.addOrder(order);
        }
    }
}
