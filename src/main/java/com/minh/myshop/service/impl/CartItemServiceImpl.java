package com.minh.myshop.service.impl;

import com.minh.myshop.dto.CartItemDto;
import com.minh.myshop.entity.CartItem;
import com.minh.myshop.entity.CartItemId;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.exception.OutOfQuantityInStock;
import com.minh.myshop.repository.CartItemRepository;
import com.minh.myshop.repository.CartRepository;
import com.minh.myshop.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Lazy
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    private final ProductServiceImpl productService;

    @Override
    public CartItem getById(CartItemId cartProductId) {
        return cartItemRepository.findById(cartProductId).orElseThrow(() -> new NotFoundException("cartId = " + cartProductId.getCartId() + " or productId = " + cartProductId.getProductId() + " not found"));
    }

    @Override
    public CartItem getReferrer(CartItemId cartProductId) {
        return cartItemRepository.getReferenceById(cartProductId);
    }

    @Override
    public CartItemDto addCartItem(CartItem cartItem) {
        return new CartItemDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto addCartItem(CartItemDto cartItemDto) throws OutOfQuantityInStock {
        if (!productService.validateQuantity(cartItemDto.getProductId(), cartItemDto.getQuantity())) {
            throw new OutOfQuantityInStock("Sản phẩm hiện tại đã hết hàng");
        }
        CartItemId cartItemId = new CartItemId(cartItemDto.getCartId(), cartItemDto.getProductId());
        CartItem cartItem;
        if (this.existById(cartItemId)) {
            cartItem = this.getById(cartItemId);
            int newQuantity = cartItem.getQuantity() + cartItemDto.getQuantity();
            if (!productService.validateQuantity(cartItem.getCartItemId().getProductId(), newQuantity)) {
                throw new OutOfQuantityInStock(
                        "Bạn đã có " + cartItem.getQuantity() + " sản phẩm trong giỏ hàng. Không thể thêm số lượng đã chọn vào giỏ hàng vì sẽ vượt quá giới hạn mua hàng của bạn.");
            }
            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = CartItem.builder()
                    .cart(cartRepository.getReferenceById(cartItemDto.getCartId()))
                    .product(productService.getReferrer(cartItemDto.getProductId()))
                    .cartItemId(cartItemId)
                    .unitPrice(cartItemDto.getUnitPrice())
                    .quantity(cartItemDto.getQuantity())
                    .build();
        }
        return this.addCartItem(cartItem);
    }

    @Override
    public CartItemDto updateCartItem(CartItemDto cartItemDto) throws OutOfQuantityInStock {
        CartItemId cartProductId = new CartItemId(cartItemDto.getCartId(), cartItemDto.getProductId());
        CartItem cartItem = this.getById(cartProductId);
        if (!productService.validateQuantity(cartItemDto.getProductId(), cartItemDto.getQuantity())) {
            throw new OutOfQuantityInStock(
                    "Bạn đã có " + cartItem.getQuantity() + " sản phẩm trong giỏ hàng. Không thể thêm số lượng đã chọn vào giỏ hàng vì sẽ vượt quá giới hạn mua hàng của bạn.");
        }
        cartItem.setQuantity(cartItemDto.getQuantity());
        return this.addCartItem(cartItem);
    }

    @Override
    public void deleteCartItem(CartItemId cartItemId) {
        var cartProduct = this.getById(cartItemId);
        cartItemRepository.delete(cartProduct);
    }

    @Override
    public List<CartItemDto> getAllItemByCartId(Integer cartId) {
        return cartItemRepository.findAllByCart_cartId(cartId).stream().map(CartItemDto::new).toList();
    }

    @Override
    public List<CartItemDto> getAllItemByUserId(Integer userId) {
        var cart = cartRepository.findCartByUser_userId(userId).orElseThrow();
        return this.getAllItemByCartId(cart.getCartId());
    }

    @Override
    public boolean existById(CartItemId cartProductId) {
        return cartItemRepository.existsById(cartProductId);
    }
}
