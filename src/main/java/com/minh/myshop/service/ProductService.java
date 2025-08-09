package com.minh.myshop.service;

import com.minh.myshop.dto.CartProductDto;
import com.minh.myshop.entity.Product;
import com.minh.myshop.enums.SortOrder;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.exception.ProductStockInvalid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product getById(Integer id) throws NotFoundException;

    Product getReferrerById(Integer id);

    List<Product> getAll();

    Page<Product> getAll(int pageNumber, int pageSize, SortOrder sortOrder);

    Page<Product> getAllByShopId(Integer shopId,int pageNumber, int pageSize, SortOrder sortOrder);

    void changeProductStock(Integer id, int quantity) throws Exception;

    void changeListProductStock(List<CartProductDto> cartProductDtoList) throws ProductStockInvalid;

    boolean validateQuantity(Integer productId, int quantity);
}
