package com.minh.myshop.service.impl;

import com.minh.myshop.entity.Category;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.repository.CategoryRepository;
import com.minh.myshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("category not found with id = " + id));
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
