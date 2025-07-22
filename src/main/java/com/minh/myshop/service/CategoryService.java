package com.minh.myshop.service;

import com.minh.myshop.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    Category findById(Integer id);

    List<Category> getAllCategory();
}
