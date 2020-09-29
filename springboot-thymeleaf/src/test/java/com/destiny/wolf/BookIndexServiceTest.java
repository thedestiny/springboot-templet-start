package com.destiny.wolf;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.destiny.wolf.entity.BookIndex;
import com.destiny.wolf.entity.PersonProfile;
import com.destiny.wolf.repository.BookIndexRepository;
import com.destiny.wolf.service.BookIndexService;
import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
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
	
	@Autowired
	public RestHighLevelClient client;
	
	protected static final RequestOptions COMMON_OPTIONS;
	
	static {
		RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
		// 默认缓冲限制为100MB，此处修改为30MB。
		builder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
		COMMON_OPTIONS = builder.build();
	}
	
	@Autowired
	private BookIndexService bookIndexService;
	

	
	@Test
	public void test001() {
		
		
		List<BookIndex> indexList = new ArrayList<>();
		Snowflake flake = IdUtil.createSnowflake(1, 1);
		
		List<String> tags = Lists.newArrayList("科幻", "悬疑", "探案", "小说", "散文", "科普", "报告", "绘画", "社科", "地理", "教育", "励志", "战争", "人文");
		
		for (int i = 0; i < 30; i++) {
			
			Faker instance = Faker.instance(Locale.CHINA);
			Book bk = instance.book();
			BookIndex index = new BookIndex();
			index.setId(flake.nextIdStr());
			index.setName(bk.title());
			index.setAddress(instance.address().fullAddress());
			index.setDescribe(instance.company().catchPhrase());
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
		
		// bookIndexService.insertBach(indexList);
		
	}
	
	// https://blog.csdn.net/yuhui123999/article/details/105202140
	
	
	@Test
	public void test002() throws IOException {
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		sourceBuilder.query(queryBuilder);
		sourceBuilder.from(0);
		sourceBuilder.size(10);
		sourceBuilder.timeout(new TimeValue(40, TimeUnit.SECONDS));
		
		SearchRequest request = new SearchRequest();
		request.indices("book_index");
		
		// 添加到source 中
		// request.types("");
		request.source(sourceBuilder);
		
		IndexRequest request1 = new IndexRequest();
		
		
		SearchResponse search = client.search(request, COMMON_OPTIONS);
		SearchHit[] hits = search.getHits().getHits();
		
		
		log.info("result is {}", JSONObject.toJSONString(search));
		
		// https://www.cnblogs.com/reycg-blog/p/9931482.html
		// https://www.cnblogs.com/reycg-blog/p/9946821.html
		// https://www.cnblogs.com/reycg-blog/p/9993094.html
		SearchSourceBuilder sourceBuilder1 = new SearchSourceBuilder();
		sourceBuilder1.query(QueryBuilders.termQuery("title","ddd"));
		
	}
	
	@Test
	public void test003() {
		
		/**
		 * 排序字段
		 * {
		 *     "sort": [
		 *         {
		 *           "FIELD": {
		 *             "order": "desc"
		 *           }
		 *         }
		 *     ]
		 * }
		 *
		 * */
		SortBuilder sortBuilder = SortBuilders.fieldSort("_id")   //排序字段
				.order(SortOrder.ASC);
		// 显示和不显示的字段
		FetchSourceFilter sourceFilter = new FetchSourceFilter(null, null);
		/**
		 *     "math":{
		 *         "FIELD": "TEXT"     //字段:值
		 *     }
		 * */
		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchQuery("name", "sky"))
				.withPageable(PageRequest.of(1, 1))
				.withHighlightFields(new HighlightBuilder.Field("sky"))
				.withSourceFilter(sourceFilter)
				.withSort(sortBuilder)
				.build();
		log.info("query is {}", searchQuery.toString());
		
		
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		PageRequest page = PageRequest.of(2, 10);
		Iterable<BookIndex> search = repository.search(queryBuilder, page);
		
		SearchHits<BookIndex> search1 = template.search(searchQuery, BookIndex.class);
		
		
		log.info("response {}", search);
		log.info("response {}", search1);
		
		
		// Bool查询现在包括四种子句:must，filter,should,must_not
		// filter 比query 快 1 避免算分 2 会缓存结果
		
		// withFilter withQuery
		NativeSearchQuery searchQuery1 = new NativeSearchQueryBuilder()
				.withFilter(boolQuery().should(matchQuery("price", 170)))
				.build();

		// 关键字查询,不分词
		QueryBuilders.termQuery("name", "小明");
		// .addAggregation(
				AggregationBuilders.terms("all_tags")
						.field("tags");
		// 支持 collection
		QueryBuilders.termsQuery("name", "小明", "小五");
		
		// text 查询 分词
		QueryBuilders.matchQuery("title","平凡的世界");
		// text 查询 分词
		QueryBuilders.multiMatchQuery("title","平凡的世界","路遥").slop(0);
		
		// 模糊查询 动态将关键词前后增加或者删除一个词进行匹配  模糊查询
		QueryBuilders.fuzzyQuery("title", "开发开放")
				.fuzziness(Fuzziness.ONE)
				.prefixLength(3) // 前缀查询的长度
				.maxExpansions(10); // max expansion 选项，用来控制模糊查询
		
		// 前缀匹配
		QueryBuilders.prefixQuery("title", "开发开放");
		
		// * 是多个 ？ 单个字符
		QueryBuilders.wildcardQuery("title", "开*放");
		QueryBuilders.wildcardQuery("title", "开？放");
		// 短语匹配
		QueryBuilders.matchPhraseQuery("title", "开放");
		
		// fuzzyQuery、prefixQuery、wildcardQuery 不支持分词查询
		
		//闭区间查询
		QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2");
        //开区间查询，默认是true，也就是包含
		QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2").includeUpper(false).includeLower(false);
        //大于 gt lt gte lte
		QueryBuilders.rangeQuery("fieldName").gt("fieldValue");
		
		QueryBuilders.boolQuery().must();//文档必须完全匹配条件，相当于and
		QueryBuilders.boolQuery().mustNot();//文档必须不匹配条件，相当于not
		QueryBuilders.boolQuery().should();//至少满足一个条件，这个文档就符合should，相当于or
	}
	
	@Test
	public void test004(){
		
		String profiles = ResourceUtil.readUtf8Str("file/person_profile.json");
		
		List<PersonProfile> profiles1 = JSONArray.parseArray(profiles, PersonProfile.class);
		Snowflake snowflake = IdUtil.createSnowflake(1, 1);
		
		profiles1.forEach(node -> {
			node.setId(snowflake.nextId());
		});
		
		
		template.save(profiles1);
	}
	
	public static void main(String[] args) {
	
	
	
	
	}
	
	
}
