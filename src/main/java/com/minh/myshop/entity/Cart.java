package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "carts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue
    Integer cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
    @JsonIgnore
    @Builder.Default
    List<CartItem> items = new ArrayList<>();

}
