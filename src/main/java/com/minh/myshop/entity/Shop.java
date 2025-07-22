package com.minh.myshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue
    @Column(name = "shop_id")
    Integer shopId;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "shop_name", length = 50)
    String shopName;

    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
