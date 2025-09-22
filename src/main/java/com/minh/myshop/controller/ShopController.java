package com.minh.myshop.controller;

import com.minh.myshop.dto.ShopDto;
import com.minh.myshop.service.impl.ShopServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Lazy
@RequestMapping("/api/users/{userId}/shop")
@AllArgsConstructor
public class ShopController {

    private final ShopServiceImpl shopService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_STOREKEEPER')")
    ResponseEntity<?> getUserShop(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(new ShopDto(shopService.getByUserId(userId)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<?> createShop(@PathVariable(name = "userId") Integer userId, @RequestBody ShopDto shopDto) {
        return ResponseEntity.ok(shopService.addShop(shopService.createShop(userId, shopDto)));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_STOREKEEPER')")
    ResponseEntity<?> updateShop(@RequestBody ShopDto shopDto) {
        return ResponseEntity.ok(shopService.updateShop(shopDto));
    }

    @GetMapping("/statistic")
    @PreAuthorize("hasRole('ROLE_STOREKEEPER')")
    ResponseEntity<?> getShopStatistic(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(shopService.getShopStatisticById(shopService.getByUserId(userId).getShopId()));
    }
}
