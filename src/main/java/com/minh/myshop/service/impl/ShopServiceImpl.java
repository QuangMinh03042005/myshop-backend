package com.minh.myshop.service.impl;

import com.minh.myshop.dto.ShopDto;
import com.minh.myshop.dto.ShopStatisticsDto;
import com.minh.myshop.entity.Shop;
import com.minh.myshop.enums.ERole;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.repository.ProductRepository;
import com.minh.myshop.repository.RoleRepository;
import com.minh.myshop.repository.ShopRepository;
import com.minh.myshop.repository.UserRepository;
import com.minh.myshop.service.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Shop getById(Integer shopId) {
        return shopRepository.findById(shopId).orElseThrow(() -> new NotFoundException("shop not found with id = " + shopId));
    }

    @Override
    public Shop getByProductId(Integer productId) {
        return productRepository.findById(productId).orElseThrow().getShop();
    }

    @Override
    public Shop getByUserId(Integer userId) {
        return shopRepository.findByUser_userId(userId).orElseThrow(() -> new NotFoundException("user with id = " + userId + " is not store keeper"));
    }

    @Override
    public Shop getReferrer(Integer shopId) {
        return shopRepository.getReferenceById(shopId);
    }

    @Override
    public Shop createShop(Integer userId, ShopDto shopDto) {
        var shop = Shop.builder().user(userRepository.getReferenceById(userId)).build();
        shop.loadFromDto(shopDto);
        return shop;
    }

    @Override
    public ShopDto addShop(Shop shop) {
        shop.getUser().getRoles().add(roleRepository.findByName(ERole.ROLE_STOREKEEPER));
        userRepository.save(shop.getUser());
        return new ShopDto(shopRepository.save(shop));
    }

    @Override
    public ShopDto updateShop(ShopDto shopDto) {
        var shop = this.getById(shopDto.getShopId());
        shop.loadFromDto(shopDto);
        return new ShopDto(shop);
    }

    @Override
    public ShopStatisticsDto getShopStatisticById(Integer shopId) {
        return shopRepository.getShopStatistics(shopId);
    }
}
