package com.platform.itcast.dao;

import com.platform.itcast.pojo.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ItemDao extends JpaRepository<Item,Long> {

    @Query("select u from Item u  where u.sku = ?1")
    Item queryBySkuId(Long skuId);

    @Query(value = "select s from Item s where s.sku in (:skus)")
    List<Item> findBySkuIn(@Param("skus") List<Long> skus);

    Page<Item> findByTitleContains(String title, Pageable pageable);

}
