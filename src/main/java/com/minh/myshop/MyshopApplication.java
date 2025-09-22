package com.minh.myshop;

import com.minh.myshop.mapper.OrderItemMapper;
import com.minh.myshop.mapper.UserMapper;
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
    private final ShopRepository shopRepository;
    private final UserMapper userMapper;
    private final OrderItemMapper orderItemMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println(shopRepository.getShopStatistics(1));
    }
}
