package com.minh.myshop.service;

import com.minh.myshop.dto.ShopDto;
import com.minh.myshop.dto.ShopStatisticsDto;
import com.minh.myshop.entity.Shop;
import org.springframework.stereotype.Service;

@Service
public interface ShopService {
    Shop getById(Integer shopId);

    Shop getByProductId(Integer productId);

    Shop getByUserId(Integer userId);

    Shop getReferrer(Integer shopId);

    Shop createShop(Integer userId, ShopDto shopDto);

    ShopDto addShop(Shop shop);

    ShopDto updateShop(ShopDto shopDto);

    ShopStatisticsDto getShopStatisticById(Integer shopId);

}
