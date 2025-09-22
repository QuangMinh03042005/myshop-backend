package com.minh.myshop.mapper;

import com.minh.myshop.dto.OrderDto;
import com.minh.myshop.entity.Order;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);


    @Mapping(target = "items", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order toEntity(OrderDto orderDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDto(OrderDto dto, @MappingTarget Order entity);
}
