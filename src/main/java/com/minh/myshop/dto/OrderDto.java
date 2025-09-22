package com.minh.myshop.dto;

import com.minh.myshop.enums.OrderStatus;
import com.minh.myshop.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer orderId;                        // ID đơn hàng
    private Integer userId;                    // Ai đặt hàng (user)

    private List<OrderItemDto> items;       // Danh sách sản phẩm trong đơn hàng
    private BigDecimal totalAmount;         // Tổng tiền đơn hàng

    private PaymentMethod paymentMethod;    // Hình thức thanh toán
    private OrderStatus orderStatus;             // Trạng thái đơn hàng

    private String shippingAddress;         // Địa chỉ giao hàng
    private LocalDateTime createdAt;        // Thời điểm tạo
    private LocalDateTime updatedAt;        // Thời điểm cập nhật
}
