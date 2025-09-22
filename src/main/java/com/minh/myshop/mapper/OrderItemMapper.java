package com.minh.myshop.mapper;

import com.minh.myshop.dto.OrderItemDto;
import com.minh.myshop.entity.CartItem;
import com.minh.myshop.entity.OrderItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem item);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toEntity(OrderItemDto dto);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toEntity(CartItem cartItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderItemFromDto(OrderItemDto dto, @MappingTarget OrderItem entity);
}
