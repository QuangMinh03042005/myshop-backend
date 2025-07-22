package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NegativeOrZero;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

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
}
