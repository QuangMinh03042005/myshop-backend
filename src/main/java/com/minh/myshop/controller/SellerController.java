package com.minh.myshop.controller;

import com.minh.myshop.dto.ShopDto;
import com.minh.myshop.service.impl.ShopServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
@AllArgsConstructor
public class SellerController {

    private final ShopServiceImpl shopService;

    @GetMapping("/{shopId}")
    ResponseEntity<?> getShop(@PathVariable(name = "shopId") Integer shopId) {
        return ResponseEntity.ok(new ShopDto(shopService.getById(shopId)));
    }

    @GetMapping("/getShopFromProduct")
    ResponseEntity<?> getShopFromProduct(@RequestParam(name = "productId") Integer productId) {
        return ResponseEntity.ok(new ShopDto(shopService.getByProductId(productId)));
    }
}
