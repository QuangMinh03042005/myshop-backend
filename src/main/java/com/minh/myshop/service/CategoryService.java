package com.minh.myshop.service;

import com.minh.myshop.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category getById(Integer id);

    List<Category> getAllCategory();
}
