package com.minh.myshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopStatisticsDto {
    private BigDecimal totalRevenue;   // tổng doanh thu
    private Long totalOrders;          // tổng số đơn hàng
    private Long totalProductsSold;    // tổng số sản phẩm bán ra
}
