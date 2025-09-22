package com.minh.myshop.service.impl;

import com.minh.myshop.dto.OrderItemDto;
import com.minh.myshop.dto.ProductDto;
import com.minh.myshop.entity.Product;
import com.minh.myshop.enums.SortOrder;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.exception.ProductStockInvalid;
import com.minh.myshop.repository.CategoryRepository;
import com.minh.myshop.repository.ProductRepository;
import com.minh.myshop.repository.ShopRepository;
import com.minh.myshop.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Lazy
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product getById(Integer id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("product not found with id = " + id));
    }

    @Override
    public Product getReferrer(Integer id) {
//        return productRepository.findReferrerByProductId(id).orElseThrow(() -> new NotFoundException("product not found with id = " + id));
        return productRepository.getReferenceById(id);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        var p = new Product();
        p.loadFromDto(productDto);
        p.setShop(shopRepository.getReferenceById(productDto.getShopId()));
        p.setCategory(categoryRepository.getReferenceById(productDto.getCategoryId()));
        return p;
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        var p = this.getById(productDto.getProductId());
        p.loadFromDto(productDto);
        return this.addProduct(p);
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
        if (sortOrder == SortOrder.DESC) {
            return productRepository.findAllByShop_shopIdOrderByCreatedAtDesc(shopId, pageable);
        }
        return productRepository.findAllByShop_shopIdOrderByCreatedAtAsc(shopId, pageable);
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
    public void changeListProductStock(List<OrderItemDto> itemDtos) throws ProductStockInvalid {
        for (var cartProductDto : itemDtos) {
            changeProductStock(cartProductDto.getProductId(), -cartProductDto.getQuantity());
        }

    }

    @Override
    public boolean validateQuantity(Integer productId, int quantity) {
        return productRepository.existsByProductIdAndQuantityInStockGreaterThanEqual(productId, quantity);
    }
}
