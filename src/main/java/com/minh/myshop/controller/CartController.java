package com.minh.myshop.controller;

import com.minh.myshop.dto.CartItemDto;
import com.minh.myshop.entity.CartItemId;
import com.minh.myshop.exception.OutOfQuantityInStock;
import com.minh.myshop.service.impl.CartItemServiceImpl;
import com.minh.myshop.service.impl.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Lazy
@RequestMapping("/api/users/{userId}/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;
    private final CartItemServiceImpl cartItemService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<?> getCart(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @GetMapping("/items")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<?> getCartItem(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(cartItemService.getAllItemByUserId(userId));
    }

    @PostMapping("/items")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<?> addItemToCart(@RequestBody CartItemDto cartItemDto) throws OutOfQuantityInStock {
        cartItemService.addCartItem(cartItemDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/items")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<?> updateProductFromCart(@RequestBody CartItemDto cartItemDto) throws OutOfQuantityInStock {
        cartItemService.updateCartItem(cartItemDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<?> removeProductFromCart(@RequestBody CartItemId cartProductId) {
        cartItemService.deleteCartItem(cartProductId);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/checkoutCart/{userId}")
//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
//    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
//    ResponseEntity<?> checkoutCart(@PathVariable("userId") Integer userId, @RequestBody List<CartItemDto> cartProductDtoList) throws ProductStockInvalid {
////         1. change all product stock
//        productService.changeListProductStock(cartProductDtoList);
//        // 2. create a new order
//        System.out.println(cartProductDtoList);
//        Order order = Order.builder().user(userService.getReferrer(userId)).build();
//        // 3. save it to db and get it's id
//        var savedOrder = orderRepository.saveAndFlush(order);
//        var orderId = savedOrder.getOrderId();
//        // 4. map cartProductDto to OrderProduct
//        List<OrderItem> orderProductList = cartProductDtoList.stream().map(cartProductDto -> new OrderItem(cartProductDto, orderService.getReferrer(orderId), orderId, productService.getReferrer(cartProductDto.getProductId()))).toList();
//        // 5. save orderProductList
//        orderItemRepository.saveAll(orderProductList);
//        // 6. set status
//        savedOrder.setOrderStatus(OrderStatus.COMPLETED);
//        // 7 . calculate total amount and set
//        var totalAmount = cartProductDtoList.stream()
//                .map(CartItemDto::getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        System.out.println(totalAmount);
//        savedOrder.setTotalAmount(totalAmount);
//        // 8 . save order to db
//        orderRepository.save(savedOrder);
//        // 9. delete products in cartProduct
//        cartItemRepository.deleteAllById(cartProductDtoList.stream().map(cartProductDto -> new CartItemId(cartProductDto.getCartId(), cartProductDto.getProductId())).toList());
//        return ResponseEntity.ok().build();
//    }
}
