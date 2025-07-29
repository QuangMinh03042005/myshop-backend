package com.minh.myshop.service;

import com.minh.myshop.entity.Shop;
import org.springframework.stereotype.Service;

@Service
public interface ShopService {
    Shop getById(Integer shopId);

    Shop getByProductId(Integer productId);
}
