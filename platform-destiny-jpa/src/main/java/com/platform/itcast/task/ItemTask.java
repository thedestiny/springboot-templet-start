package com.platform.itcast.task;

import com.platform.itcast.pojo.Item;
import com.platform.itcast.service.ItemService;
import com.platform.itcast.util.CrawlerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemTask {

    @Autowired
    private ItemService itemService;


    //当下载任务完成后，间隔多长时间进行下一次的任务。
    // @Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws Exception {
        //声明需要解析的初始地址
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&cid2=653&cid3=655&s=113&click=0&page=";

        //按照页面对手机的搜索结果进行遍历解析
        for (int i = 1; i < 20; i = i + 1) {

            List<String> handleMain = CrawlerUtils.handleMain(url + i);

            for (String detail : handleMain) {

                List<Item> items = CrawlerUtils.handleDetail(detail);
                itemService.saveAll(items);
            }




        }


        System.out.println("手机数据抓取完成！");


    }

}
