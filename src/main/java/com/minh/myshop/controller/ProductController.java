package com.minh.myshop.controller;

import com.minh.myshop.dto.ProductDto;
import com.minh.myshop.dto.ShopDto;
import com.minh.myshop.enums.SortOrder;
import com.minh.myshop.exception.ProductNotFoundException;
import com.minh.myshop.service.impl.ProductServiceImpl;
import com.minh.myshop.service.impl.ShopServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Lazy
@RequestMapping("/api/users/{userId}/shop/products")
@AllArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private final ShopServiceImpl shopService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_STOREKEEPER')")
    ResponseEntity<?> getAllProduct(
            @PathVariable(name = "userId") Integer userId,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "48") int pageSize
    ) {
        System.out.println(pageNumber + " " + pageSize);
        return ResponseEntity.ok(
                productService
                        .getAllByShopId(shopService.getByUserId(userId).getShopId(), pageNumber, pageSize, SortOrder.DESC)
                        .map(ProductDto::new)
        );
    }

    @GetMapping("/{productId}")
    ResponseEntity<?> getProduct(@PathVariable(name = "productId") Integer productId)
            throws ProductNotFoundException {
        return ResponseEntity.ok(new ProductDto(productService.getById(productId)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_STOREKEEPER')")
    ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.addProduct(productService.createProduct(productDto)));
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_STOREKEEPER')")
    ResponseEntity<?> updateProduct(@PathVariable(name = "productId") Integer productId, @RequestBody ProductDto productDto) {
        productDto.setProductId(productId);
        return ResponseEntity.ok(productService.updateProduct(productDto));
    }

    @GetMapping("/{productId}/shop")
    ResponseEntity<?> getShopFromProduct(
            @PathVariable(name = "productId") Integer productId
    ) {
        return ResponseEntity.ok(
                new ShopDto(shopService.getByProductId(productId))
        );
    }
}
