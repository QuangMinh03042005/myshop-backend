package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minh.myshop.enums.EPaymentMethod;
import com.minh.myshop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

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
    @Column(name = "updated_at", updatable = false)
    Date updatedAt;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "to_location")
    String toLocation;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "payment_method")
    EPaymentMethod paymentMethod = EPaymentMethod.CASH;

    public void addProduct(Product product, int count) {
        OrderProduct orderProduct = new OrderProduct(this, product, count);
    }
}
