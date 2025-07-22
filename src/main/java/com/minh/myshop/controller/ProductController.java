package com.minh.myshop.controller;

import com.minh.myshop.dto.ProductDto;
import com.minh.myshop.exception.ProductNotFoundException;
import com.minh.myshop.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductServiceImpl productService;

    @GetMapping
    ResponseEntity<?> getAllProduct(@RequestParam(name = "pageNumber",defaultValue = "0") int pageNumber, @RequestParam(name = "pageSize",defaultValue = "48") int pageSize) {
        System.out.println(pageNumber + " " + pageSize);
        return ResponseEntity.ok(productService.getAllProduct(pageNumber, pageSize)
                .map(ProductDto::new)
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getProduct(@PathVariable Integer id) throws ProductNotFoundException {
        return ResponseEntity.ok(new ProductDto(productService.getProductById(id)));
    }

    @PostMapping("/newProduct")
    @PreAuthorize("hasRole('ROLE_STOREKEEPER')")
    ResponseEntity<?> addProduct() {
        return ResponseEntity.ok().build();
    }

}
