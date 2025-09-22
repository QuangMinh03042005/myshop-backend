package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minh.myshop.enums.OrderStatus;
import com.minh.myshop.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @Column(name = "updated_at", updatable = false)
    Date updatedAt;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "shipping_address")
    String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "payment_method")
    PaymentMethod paymentMethod = PaymentMethod.COD;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonIgnore
    @Builder.Default
    List<OrderItem> items = new ArrayList<>();

}
