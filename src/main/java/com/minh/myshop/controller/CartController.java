package com.minh.myshop.controller;

import com.minh.myshop.dto.CartDto;
import com.minh.myshop.dto.CartProductDto;
import com.minh.myshop.entity.CartProduct;
import com.minh.myshop.entity.CartProductId;
import com.minh.myshop.entity.Order;
import com.minh.myshop.entity.OrderProduct;
import com.minh.myshop.enums.OrderStatus;
import com.minh.myshop.exception.OutOfQuantityInStock;
import com.minh.myshop.exception.ProductStockInvalid;
import com.minh.myshop.repository.*;
import com.minh.myshop.service.impl.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final UserServiceImpl userService;
    private final CartServiceImpl cartService;
    private final CartProductServiceImpl cartProductService;
    private final ProductServiceImpl productService;
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
    ResponseEntity<?> addProductToCart(@RequestBody CartProductDto cartProductDto) throws OutOfQuantityInStock {
        CartProduct cartProduct = null;
        CartProductId cartProductId = new CartProductId(cartProductDto.getCartId(), cartProductDto.getProductId());
        if (cartProductService.existById(cartProductId)) {
            cartProduct = cartProductService.getById(cartProductId);
            int newQuantity = cartProduct.getQuantity() + cartProductDto.getQuantity();
            if (!productService.validateQuantity(cartProduct.getCartProductId().getProductId(), newQuantity)) {
                throw new OutOfQuantityInStock("\n" +
                        "Bạn đã có " + cartProduct.getQuantity() + " sản phẩm trong giỏ hàng. Không thể thêm số lượng đã chọn vào giỏ hàng vì sẽ vượt quá giới hạn mua hàng của bạn.");
            }
            cartProduct.setQuantity(newQuantity);
        } else {
            cartProduct = CartProduct.builder()
                    .cart(cartService.getReferrerById(cartProductDto.getCartId()))
                    .product(productService.getReferrerById(cartProductDto.getProductId()))
                    .cartProductId(cartProductId)
                    .unitPrice(cartProductDto.getUnitPrice())
                    .quantity(cartProductDto.getQuantity())
                    .totalPrice(cartProductDto.getTotalPrice())
                    .build();
        }
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
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    ResponseEntity<?> buyFromCart(@PathVariable("userId") Integer userId, @RequestBody List<CartProductDto> cartProductDtoList) throws ProductStockInvalid {
//         1. change all product stock
        productService.changeListProductStock(cartProductDtoList);
        // 2. create a new order
        System.out.println(cartProductDtoList);
        Order order = Order.builder().user(userService.getReferrerById(userId)).build();
        // 3. save it to db and get it's id
        var savedOrder =  orderRepository.saveAndFlush(order);
        var orderId = savedOrder.getOrderId();
        // 4. map cartProductDto to OrderProduct
        List<OrderProduct> orderProductList = cartProductDtoList.stream().map(cartProductDto -> new OrderProduct(cartProductDto, orderService.getReferrerById(orderId), orderId, productService.getReferrerById(cartProductDto.getProductId()))).toList();
        // 5. save orderProductList
        orderProductRepository.saveAll(orderProductList);
        // 6. set status
        savedOrder.setOrderStatus(OrderStatus.COMPLETED);
        // 7 . calculate total amount and set
        var totalAmount = cartProductDtoList.stream()
                .map(CartProductDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(totalAmount);
        savedOrder.setTotalAmount(totalAmount);
        // 8 . save order to db
        orderRepository.save(savedOrder);
        // 9. delete products in cartProduct
        cartProductRepository.deleteAllById(cartProductDtoList.stream().map(cartProductDto -> new CartProductId(cartProductDto.getCartId(), cartProductDto.getProductId())).toList());
        return ResponseEntity.ok().build();
    }
}
