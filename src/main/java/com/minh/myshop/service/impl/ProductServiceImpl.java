package com.minh.myshop.service.impl;

import com.minh.myshop.dto.CartProductDto;
import com.minh.myshop.dto.OrderProductDto;
import com.minh.myshop.entity.CartProduct;
import com.minh.myshop.entity.Product;
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
    public Product getProductById(Integer id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("product not found with id = " + id));
    }

    @Override
    public Product getReferrerById(Integer id) {
//        return productRepository.findReferrerByProductId(id).orElseThrow(() -> new NotFoundException("product not found with id = " + id));
        return productRepository.getReferenceById(id);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProduct(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public void changeProductStock(Integer id, int quantity) throws ProductStockInvalid {
        var product = getProductById(id);
        int newStock = product.getQuantityInStock() + quantity;
        if (newStock < 0) {
            throw new ProductStockInvalid("modafuck! đéo còn " + product.getProductName() + " cho mày mua");
        }
        product.setQuantityInStock(newStock);
        productRepository.save(product);
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = ProductStockInvalid.class)
    public void changeListProductStock(List<CartProductDto> cartProductDtoList) throws ProductStockInvalid {
        Product product = null;
        for (var cartProductDto : cartProductDtoList) {
            changeProductStock(cartProductDto.getProductId(), -cartProductDto.getQuantity());
        }

    }
}
