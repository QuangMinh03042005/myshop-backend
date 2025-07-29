package com.minh.myshop.service.impl;

import com.minh.myshop.entity.Shop;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.repository.ShopRepository;
import com.minh.myshop.service.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ProductServiceImpl productService;
    private final ShopRepository shopRepository;

    @Override
    public Shop getById(Integer shopId) {
        return shopRepository.findById(shopId).orElseThrow(() -> new NotFoundException("shop not found with id = " + shopId));
    }

    @Override
    public Shop getByProductId(Integer productId) {
        var product = productService.getById(productId);
        return product.getShop();
    }
}
