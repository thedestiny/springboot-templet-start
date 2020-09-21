package com.destiny.wolf;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.destiny.wolf.service.BookIndexService;
import com.destiny.wolf.entity.BookIndex;
import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookIndexServiceTest extends WolfApplicationTests {
	
	@Autowired
	private BookIndexService bookIndexService;
	
	@Test
	public void test001() {
		
		List<BookIndex> indexList = new ArrayList<>();
		Snowflake flake = IdUtil.createSnowflake(1, 1);
		
		List<String> tags = Lists.newArrayList("科幻", "悬疑", "探案", "小说", "散文", "科普", "报告", "绘画", "社科", "地理", "教育", "励志", "战争", "人文");
		
		for (int i = 0; i < 10; i++) {
			
			Faker instance = Faker.instance(Locale.CHINA);
			Book bk = instance.book();
			BookIndex index = new BookIndex();
			index.setId(flake.nextIdStr());
			index.setName(bk.title());
			index.setPublishDate(instance.date().between(new Date(100, 1, 1), new Date(120, 1, 1)));
			index.setPrice(RandomUtil.randomBigDecimal(BigDecimal.valueOf(1), BigDecimal.valueOf(2000)));
			index.setNumber(RandomUtil.randomInt(100, 30000));
			index.setAuthor(bk.author());
			
			List<String> list = Lists.newArrayList();
			
			int num = RandomUtil.randomInt(1, 8);
			for (int j = 0; j < num; j++) {
				int cnt = RandomUtil.randomInt(0, 13);
				list.add(tags.get(cnt));
			}
			
			list = list.stream().distinct().collect(Collectors.toList());
			index.setTags(list);
			indexList.add(index);
			
			log.info(" book is {}", JSONObject.toJSONString(index));
		}
		
		bookIndexService.insertBach("book_index", indexList);
		
	}
	
	
}
