package com.minh.myshop.controller;

import com.minh.myshop.dto.ProductDto;
import com.minh.myshop.dto.ShopDto;
import com.minh.myshop.enums.SortOrder;
import com.minh.myshop.service.impl.ProductServiceImpl;
import com.minh.myshop.service.impl.ShopServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Lazy
@RequestMapping("/api")
@AllArgsConstructor
public class GlobalController {

    private final ProductServiceImpl productService;
    private final ShopServiceImpl shopService;

    @GetMapping("/products")
    ResponseEntity<?> getAllProduct(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "48") int pageSize
    ) {
        System.out.println(pageNumber + " " + pageSize);
        return ResponseEntity.ok(
                productService
                        .getAll(pageNumber, pageSize, SortOrder.DESC)
                        .map(ProductDto::new)
        );
    }

    @GetMapping("/shops/{shopId}")
    ResponseEntity<?> getShop(@PathVariable Integer shopId) {
        return ResponseEntity.ok(new ShopDto(shopService.getById(shopId)));
    }
}
