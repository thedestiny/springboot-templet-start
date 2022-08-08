package com.platform.itcast.web;

import com.platform.itcast.pojo.Item;
import com.platform.itcast.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class IndexController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "list")
    public Page<Item> queryPage(String title) {
        log.info(" title is {} ", title);
        return itemService.findByTitle(title);
    }


}
