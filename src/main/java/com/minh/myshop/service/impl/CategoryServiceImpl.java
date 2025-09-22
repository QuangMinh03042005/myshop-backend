package com.minh.myshop.service.impl;

import com.minh.myshop.entity.Category;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.repository.CategoryRepository;
import com.minh.myshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category getById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("category not found with id = " + categoryId));
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getReferrer(Integer categoryId) {
        return categoryRepository.getReferenceById(categoryId);
    }
}
