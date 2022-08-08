package com.platform.itcast.service;

import com.platform.itcast.pojo.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {

    /**
     * 保存商品
     * @param item
     */
    public void save(Item item);

    /**
     * 根据条件查询商品
     * @param item
     * @return
     */
    public List<Item> findAll(Item item);

    void saveAll(List<Item> items);

    Page<Item> findByTitle(String title);

}
