package com.platform.itcast.service.impl;

import com.platform.itcast.dao.ItemDao;
import com.platform.itcast.pojo.Item;
import com.platform.itcast.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    @Transactional
    public void save(Item item) {
        if (itemDao.queryBySkuId(item.getSku()) == null) {
            this.itemDao.save(item);
        }
    }

    @Override
    public Page<Item> findByTitle(String title) {
        PageRequest request = new PageRequest(1, 20);
        return itemDao.findByTitleContains(title, request);
    }

    @Override
    @Transactional
    public void saveAll(List<Item> items) {
        if (items == null || items.size() == 0) {
            return;
        }

        List<Item> bySkuIn = itemDao.findBySkuIn(items.stream().map(Item::getSku).collect(Collectors.toList()));
        if (bySkuIn == null || bySkuIn.size() == 0) {
            itemDao.saveAll(items);
        } else {
            List<Item> saveList = new ArrayList<>();
            List<Long> collect = bySkuIn.stream().map(Item::getSku).collect(Collectors.toList());
            for (Item item : items) {
                if (!collect.contains(item.getSku())) {
                    saveList.add(item);
                }
            }
            if (saveList != null && saveList.size() > 0) {
                itemDao.saveAll(saveList);
            }


        }


    }

    @Override
    public List<Item> findAll(Item item) {
        //声明查询条件
        Example<Item> example = Example.of(item);

        //根据查询条件进行查询数据
        List<Item> list = this.itemDao.findAll(example);

        return list;
    }
}
