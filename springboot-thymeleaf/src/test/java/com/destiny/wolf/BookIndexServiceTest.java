package com.destiny.wolf;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.destiny.wolf.entity.BookIndex;
import com.destiny.wolf.repository.BookIndexRepository;
import com.destiny.wolf.service.BookIndexService;
import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Slf4j
@Component
public class BookIndexServiceTest extends WolfApplicationTests {
	
	/**
	 * 简单的操作使用 ElasticsearchRepository
	 * 复杂的使用  ElasticsearchRestTemplate
	 */
	@Autowired
	private BookIndexRepository repository;
	
	@Autowired
	private ElasticsearchRestTemplate template;
	
	protected static final RequestOptions COMMON_OPTIONS;
	
	static {
		RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
		// 默认缓冲限制为100MB，此处修改为30MB。
		builder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
		COMMON_OPTIONS = builder.build();
	}
	
	@Autowired
	private BookIndexService bookIndexService;
	
	@Autowired
	public RestHighLevelClient client;
	
	@Test
	public void test001() {
		
		List<BookIndex> indexList = new ArrayList<>();
		Snowflake flake = IdUtil.createSnowflake(1, 1);
		
		List<String> tags = Lists.newArrayList("科幻", "悬疑", "探案", "小说", "散文", "科普", "报告", "绘画", "社科", "地理", "教育", "励志", "战争", "人文");
		
		for (int i = 0; i < 1000; i++) {
			
			Faker instance = Faker.instance(Locale.CHINA);
			Book bk = instance.book();
			BookIndex index = new BookIndex();
			index.setId(flake.nextIdStr());
			index.setName(bk.title());
			index.setPublishDate(instance.date().between(new Date(100, 1, 1), new Date(120, 1, 1)));
			index.setPrice(RandomUtil.randomBigDecimal(BigDecimal.valueOf(1), BigDecimal.valueOf(2000)).setScale(2, BigDecimal.ROUND_HALF_UP));
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
		
		bookIndexService.insertBach(indexList);
		
	}
	
	// https://blog.csdn.net/yuhui123999/article/details/105202140
	
	
	@Test
	public void test002() throws IOException {
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		sourceBuilder.query(queryBuilder);
		sourceBuilder.from(0);
		sourceBuilder.size(10);
		
		SearchRequest request = new SearchRequest();
		request.indices("book_index");
		
		// request.types("");
		request.source(sourceBuilder);
		
		
		SearchResponse search = client.search(request, COMMON_OPTIONS);
		log.info("result is {}", JSONObject.toJSONString(search));
		
		
	}
	
	@Test
	public void test003() {
		
		SortBuilder sortBuilder = SortBuilders.fieldSort("_id")   //排序字段
				.order(SortOrder.ASC);
		
		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchQuery("name","sky"))
				.withSort(sortBuilder)
				.build();
		
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		PageRequest page = PageRequest.of(2, 10);
		Iterable<BookIndex> search = repository.search(queryBuilder, page);
		
		SearchHits<BookIndex> search1 = template.search(searchQuery, BookIndex.class);
		
		
		
		log.info("response {}", search);
		log.info("response {}", search1);
		
		
	}
	
	
}
