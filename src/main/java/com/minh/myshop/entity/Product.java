package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    Integer productId;

    @Column(name = "product_name")
    String productName;

    @Column(name = "description")
    String description;

    @Column(name = "image")
    String image;

    @Column(name = "quantity_in_stock")
    Integer quantityInStock;

    @Column(name = "price")
    Double price;

    // uncomment cascade la` do loi~ duoc tim thay trong day
    //  https://stackoverflow.com/questions/13370221/persistentobjectexception-detached-entity-passed-to-persist-thrown-by-jpa-and-h
    @ManyToOne(fetch = FetchType.LAZY)//(cascade = CascadeType.ALL)
//    @JoinColumn(name = "category_id", nullable = false,
//            foreignKey = @ForeignKey(name = "category_id")
//    )
//    @JsonIgnore
    @JoinColumn(name = "category_id")
    Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;

    @Column(name = "deleted_at")
    Date deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    Shop shop;
}
