package com.minh.myshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Integer cartId;
    private Integer userId;
    private List<CartItemDto> items;
}
