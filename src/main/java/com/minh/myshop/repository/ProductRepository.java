package com.minh.myshop.repository;

import com.minh.myshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findReferrerByProductId(Integer productId);

    //order by createdAt DESC (newest top)
    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    //order by createdAt DESC (oldest top)
    Page<Product> findAllByOrderByCreatedAtAsc(Pageable pageable);

    // check quantity <= quantityInStock
    boolean existsByProductIdAndQuantityInStockGreaterThanEqual(Integer productId, int quantity);
}
