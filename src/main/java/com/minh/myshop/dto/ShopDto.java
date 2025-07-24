package com.minh.myshop.dto;

import com.minh.myshop.entity.Shop;
import lombok.Data;

import java.util.Date;

@Data
public class ShopDto {
    private Integer shopId;
    private String shopName;
    private String logo;
    private Date created_at;

    public ShopDto(Shop shop) {
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        this.logo = shop.getLogo();
        this.created_at = shop.getCreatedAt();
    }
}
