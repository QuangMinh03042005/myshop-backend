package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minh.myshop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    Integer orderId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status")
    OrderStatus orderStatus = OrderStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    public void addProduct(Product product, int count) {
        OrderProduct orderProduct = new OrderProduct(this, product, count);
    }
}
