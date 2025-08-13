package com.minh.myshop.service.impl;

import com.minh.myshop.dto.CartProductDto;
import com.minh.myshop.entity.Product;
import com.minh.myshop.enums.SortOrder;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.exception.ProductStockInvalid;
import com.minh.myshop.repository.ProductRepository;
import com.minh.myshop.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product getById(Integer id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("product not found with id = " + id));
    }

    @Override
    public Product getReferrerById(Integer id) {
//        return productRepository.findReferrerByProductId(id).orElseThrow(() -> new NotFoundException("product not found with id = " + id));
        return productRepository.getReferenceById(id);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAll(int pageNumber, int pageSize, SortOrder sortOrder) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (sortOrder == SortOrder.ASC) {
            return productRepository.findAllByOrderByCreatedAtAsc(pageable);
        } else if (sortOrder == SortOrder.DESC) {
            return productRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getAllByShopId(Integer shopId, int pageNumber, int pageSize, SortOrder sortOrder) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (sortOrder == SortOrder.ASC) {
            return productRepository.findAllByOrderByCreatedAtAsc(pageable);
        } else if (sortOrder == SortOrder.DESC) {
            return productRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public void changeProductStock(Integer id, int quantity) throws ProductStockInvalid {
        var product = getById(id);
        int newStock = product.getQuantityInStock() + quantity;
        if (newStock < 0) {
            throw new ProductStockInvalid("modafuck! đéo còn " + product.getProductName() + " cho mày mua");
        }
        product.setQuantityInStock(newStock);
        productRepository.save(product);
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = ProductStockInvalid.class)
    public void changeListProductStock(List<CartProductDto> cartProductDtoList) throws ProductStockInvalid {
        Product product = null;
        for (var cartProductDto : cartProductDtoList) {
            changeProductStock(cartProductDto.getProductId(), -cartProductDto.getQuantity());
        }

    }

    @Override
    public boolean validateQuantity(Integer productId, int quantity) {
        return productRepository.existsByProductIdAndQuantityInStockGreaterThanEqual(productId, quantity);
    }
}
