package com.minh.myshop.service;

import com.minh.myshop.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category getById(Integer categoryId);

    Category addCategory(Category category);

    Category getReferrer(Integer categoryId);

    List<Category> getAllCategory();
}
