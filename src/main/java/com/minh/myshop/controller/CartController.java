package com.minh.myshop.controller;

import com.minh.myshop.dto.CartDto;
import com.minh.myshop.dto.CartProductDto;
import com.minh.myshop.dto.OrderDto;
import com.minh.myshop.entity.CartProduct;
import com.minh.myshop.entity.CartProductId;
import com.minh.myshop.entity.Order;
import com.minh.myshop.entity.OrderProduct;
import com.minh.myshop.enums.OrderStatus;
import com.minh.myshop.exception.ProductStockInvalid;
import com.minh.myshop.repository.*;
import com.minh.myshop.service.ProductService;
import com.minh.myshop.service.impl.CartProductServiceImpl;
import com.minh.myshop.service.impl.CartServiceImpl;
import com.minh.myshop.service.impl.OrderServiceImpl;
import com.minh.myshop.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final UserServiceImpl userService;
    private final CartServiceImpl cartService;
    private final CartProductServiceImpl cartProductService;
    private final ProductService productService;
    private final OrderServiceImpl orderService;

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<?> getCart(@PathVariable("userId") Integer userId) {
        var cart = cartService.getCartByUserId(userId);
        var cartProductListDto = cartProductService.getAllProductByCartId(cart.getCartId()).stream().map(CartProductDto::new).toList();
        var cartDto = new CartDto(cart.getCartId(), cartProductListDto);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/addProductToCart")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<?> addProductToCart(@RequestBody CartProductDto cartProductDto) {
        System.out.println(cartProductDto);
        CartProduct cartProduct = CartProduct.builder()
                .cart(cartService.getReferrerById(cartProductDto.getCartId()))
                .product(productService.getReferrerById(cartProductDto.getProductId()))
                .cartProductId(new CartProductId(cartProductDto.getCartId(), cartProductDto.getProductId()))
                .unitPrice(cartProductDto.getUnitPrice())
                .quantity(cartProductDto.getQuantity())
                .totalPrice(cartProductDto.getTotalPrice())
                .build();
        cartProductRepository.save(cartProduct);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteProductFromCart")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<?> deleteProductFromCart(@RequestBody CartProductId cartProductId) {
        System.out.println(cartProductId);
        var cartProduct = cartProductService.getById(cartProductId);
        cartProductRepository.delete(cartProduct);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/buyFromCart/{userId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @Transactional
    ResponseEntity<?> buyFromCart(@PathVariable("userId") Integer userId, @RequestBody List<CartProductDto> cartProductDtoList) throws ProductStockInvalid {
        // 1. change all product stock
        productService.changeListProductStock(cartProductDtoList);
        // 2. create a new order and save it to db
        Order order = Order.builder().user(userService.getReferrerById(userId)).build();
        orderRepository.save(order);
        // 3. find it's id
        var orderId = orderRepository.findOrderIdByUserIdAndStatus(userId, OrderStatus.PENDING).orElseThrow();
        List<OrderProduct> orderProductList = cartProductDtoList.stream().map(cartProductDto -> new OrderProduct(cartProductDto, orderService.getReferrerById(orderId), orderId, productService.getReferrerById(cartProductDto.getProductId()), cartProductDto.getProductId())).toList();
        orderProductRepository.saveAll(orderProductList);
        order = orderService.getReferrerById(orderId);
        order.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        cartProductRepository.deleteAllById(cartProductDtoList.stream().map(cartProductDto -> new CartProductId(cartProductDto.getCartId(), cartProductDto.getProductId())).toList());
        return ResponseEntity.ok().build();
    }
}
