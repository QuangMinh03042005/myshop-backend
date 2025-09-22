package com.minh.myshop.dto;

import com.minh.myshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String storageLocation;
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
        this.storageLocation = product.getStorageLocation();
    }
}