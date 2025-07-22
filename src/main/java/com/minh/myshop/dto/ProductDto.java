package com.minh.myshop.dto;

import com.minh.myshop.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductDto {
    private Integer productId;
    private String productName;
    private String description;
    private String image;
    private Integer quantityInStock;
    private Double price;
    private Integer categoryId;
    private Integer shopId;
    private String shopName;
    private Date createdAt;
    private Date updatedAt;

    public ProductDto(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.quantityInStock = product.getQuantityInStock();
        this.price = product.getPrice();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.categoryId = product.getCategory().getCategoryId();
        this.shopId = product.getShop().getShopId();
        this.shopName = product.getShop().getShopName();
    }
}