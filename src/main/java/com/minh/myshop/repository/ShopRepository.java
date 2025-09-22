package com.minh.myshop.repository;

import com.minh.myshop.dto.ShopStatisticsDto;
import com.minh.myshop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Optional<Shop> findByUser_userId(Integer userId);

    @Query(value = """
            select sum(oi.total_price) as totalRevenue, count(distinct p.product_id) as totalProductSold, count(distinct oi.order_id) as totalOrders
            
            from order_items oi
            
            right join products p on p.product_id = oi.product_id and p.shop_id = :shopId
            """, nativeQuery = true)
    ShopStatisticsDto getShopStatistics(@Param("shopId") Integer shopId);
}
