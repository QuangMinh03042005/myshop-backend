package com.minh.myshop.service;

import com.minh.myshop.dto.CartProductDto;
import com.minh.myshop.dto.OrderProductDto;
import com.minh.myshop.entity.CartProduct;
import com.minh.myshop.entity.Product;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.exception.ProductNotFoundException;
import com.minh.myshop.exception.ProductStockInvalid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public interface ProductService {
    Product getProductById(Integer id) throws NotFoundException;

    Product getReferrerById(Integer id);

    List<Product> getAllProduct();

    Page<Product> getAllProduct(int pageNumber, int pageSize);

    void changeProductStock(Integer id, int quantity) throws Exception;

    void changeListProductStock(List<CartProductDto> cartProductDtoList) throws ProductStockInvalid;
}
