package com.destiny.wolf.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.destiny.wolf.entity.BookIndex;
import com.destiny.wolf.service.BookIndexService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BookIndexServiceImpl extends IEsServiceImpl implements BookIndexService {

	/**
	 * 索引名
	 * */
	private static final String index = "book_index";

	@Override
	public void insertBach(List<BookIndex> list) {
		if (list.isEmpty()) {
			log.warn("bach insert index but list is empty ...");
			return;
		}
		list.forEach((student)->{
			super.insertRequest(index, student.getId().toString(), student);
		});
	}

	@Override
	public List<BookIndex> searchList() {
		SearchResponse searchResponse = search(index);
		// 数据总条数
        long value = searchResponse.getHits().getTotalHits().value;
        SearchHit[] hits = searchResponse.getHits().getHits();
		List<BookIndex> lolList = new ArrayList<>();
		Arrays.stream(hits).forEach(hit -> {
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			BookIndex lol = BeanUtil.mapToBean(sourceAsMap, BookIndex.class, true);
			// 转换查询对象
			BookIndex index = JSONObject.parseObject(hit.getSourceAsString(), BookIndex.class);
			Map<String,String> map = new HashMap<>();

			lolList.add(lol);
		});
		return lolList;
	}

	protected SearchResponse search(String index) {

		SearchRequest searchRequest = new SearchRequest(index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.trackTotalHits(true);
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder
				.query(QueryBuilders.rangeQuery("level").gte("34"))
				.query(QueryBuilders.termQuery("", ""));

		//bool符合查询
		//BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
		//        .filter(QueryBuilders.matchQuery("name", "盖伦"))
		//        .must(QueryBuilders.matchQuery("desc", "部落"))
		//        .should(QueryBuilders.matchQuery("realName", "光辉"));

		BoolQueryBuilder level = QueryBuilders.boolQuery()
				.must(QueryBuilders.rangeQuery("level").gte("34").lte("45"))
				.must(QueryBuilders.termQuery("mi", "val"));
		searchSourceBuilder.query(level);


		//分页
		//searchSourceBuilder.from(1).size(2);
		// 排序
		//searchSourceBuilder.sort("", SortOrder.DESC);

		////误拼写时的fuzzy模糊搜索方法 2表示允许的误差字符数
		//QueryBuilders.fuzzyQuery("title", "ceshi").fuzziness(Fuzziness.build("2"));
		searchRequest.source(searchSourceBuilder);
		System.out.println(searchSourceBuilder.toString());
		System.out.println(JSONUtil.parseObj(searchSourceBuilder.toString()).toStringPretty());
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, COMMON_OPTIONS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return searchResponse;
	}


	public static void main(String[] args) {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.trackTotalHits(true);
//		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//		searchSourceBuilder
//				.query(QueryBuilders.rangeQuery("level").gte("34"))
//				.query(QueryBuilders.termQuery("34", "44"));

		//bool符合查询
		//BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
		//        .filter(QueryBuilders.matchQuery("name", "盖伦"))
		//        .must(QueryBuilders.matchQuery("desc", "部落"))
		//        .should(QueryBuilders.matchQuery("realName", "光辉"));

		BoolQueryBuilder level = QueryBuilders.boolQuery()
				.must(QueryBuilders.rangeQuery("level").gte("34").lte("45"))
				.must(QueryBuilders.termQuery("mi", "val"));
		searchSourceBuilder.query(level);



		System.out.println(searchSourceBuilder.toString());

	}

}
