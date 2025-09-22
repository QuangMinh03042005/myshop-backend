package com.minh.myshop.mapper;

import com.minh.myshop.dto.CartItemDto;
import com.minh.myshop.entity.CartItem;
import com.minh.myshop.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemDto toDto(CartItem cartItem);

    CartItem toEntity(CartItemDto cartItemDto);

    @Mapping(target = "cartItemId", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartItem toEntity(OrderItem orderItem);
}
