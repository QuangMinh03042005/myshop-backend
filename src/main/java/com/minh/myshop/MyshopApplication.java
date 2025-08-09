package com.minh.myshop;

import com.minh.myshop.entity.Order;
import com.minh.myshop.entity.User;
import com.minh.myshop.enums.OrderStatus;
import com.minh.myshop.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MyshopApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MyshopApplication.class, args);
    }

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        Order order = Order.builder().user(userRepository.findById(252).get()).build();
//        var savedOrder = orderRepository.saveAndFlush(order);
//        System.out.println(savedOrder.getCreatedAt());
//        System.out.println(savedOrder.getOrderStatus());
//        savedOrder.setOrderStatus(OrderStatus.COMPLETED);
//        orderRepository.save(savedOrder);
    }
}
