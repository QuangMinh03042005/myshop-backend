package com.minh.myshop.controller;

import com.minh.myshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
}
