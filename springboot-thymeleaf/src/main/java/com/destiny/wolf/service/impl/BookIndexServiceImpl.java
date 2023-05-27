package com.destiny.wolf.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.destiny.wolf.entity.BookIndex;
import com.destiny.wolf.entity.EsEnvLog;
import com.destiny.wolf.service.BookIndexService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
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

	public Map<String, List<EsEnvLog>> search(String mac, long startTime, long endTime) {
		int size = 10000;
		int hour = 2; //默认按两小时分组
		System.out.println("startTime:" + startTime);
		System.out.println("endTime:" + endTime);
		Scroll scroll = new Scroll(TimeValue.timeValueMinutes(5));
//        startTime = 1676427009000l;
//        endTime = 1676429483000l;
		SearchRequest request = new SearchRequest("index_env");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//创建查询条件
		TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("macAddress.keyword", mac);
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("timestamp").lte(endTime).gte(startTime);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(termsQueryBuilder).must(rangeQueryBuilder);
		searchSourceBuilder.query(boolQueryBuilder);
		//设置最多一次性能够查询出多少条数据
		searchSourceBuilder.size(size);
		//排序
		searchSourceBuilder.sort("timestamp", SortOrder.ASC);
		//加入scroll和构造器
		request.scroll(scroll);
		request.source(searchSourceBuilder);
		Map<String, List<EsEnvLog>> resMap = new HashMap<>();
		//存储scroll的list
		List<String> scrollIdList = new ArrayList<>();
		//返回结果存入list
		List<EsEnvLog> list = new ArrayList<>();
		try {
			// 打印 DSL
			System.out.println("DSL:" + searchSourceBuilder.toString());
			SearchResponse response = client.search(request, RequestOptions.DEFAULT);
			//拿到第一个游标
			String scrollId = response.getScrollId();
			scrollIdList.add(scrollId);
			//hits结果
			SearchHit[] hits = response.getHits().getHits();
			//滚动查询将SearchHit封装到result中
			while (ArrayUtils.isNotEmpty(hits)) {
				for (SearchHit hit : hits) {
					//Function<SearchHit, T>, 输入SearchHit，经过操作后，返回T结果
					list.add(JSON.parseObject(hit.getSourceAsString(), EsEnvLog.class));
				}
				//说明滚动完了，返回结果即可
				if (hits.length < size) {
					break;
				}
				//继续滚动，根据上一个游标，得到这次开始查询位置
				SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
				searchScrollRequest.scroll(scroll);
				//得到结果
				SearchResponse searchScrollResponse = client.scroll(searchScrollRequest, RequestOptions.DEFAULT);
				//定位游标
				scrollId = searchScrollResponse.getScrollId();
				hits = searchScrollResponse.getHits().getHits();
				scrollIdList.add(scrollId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//清理scroll,释放资源
			ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
			clearScrollRequest.setScrollIds(scrollIdList);
			try {
				client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}


		return resMap;
	}

}
