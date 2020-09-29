package com.destiny.wolf.service.impl;

import com.destiny.wolf.entity.PersonProfile;
import com.destiny.wolf.service.PersonProfileService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


@Slf4j
@Service
public class PersonProfileServiceImpl implements PersonProfileService {
	
	@Autowired
	private ElasticsearchRestTemplate template;
	
	@Autowired
	public RestHighLevelClient client;
	
	
	public List<PersonProfile> queryPersonProfile(PersonProfile entity) throws IOException {
		
		
		List<PersonProfile> result = Lists.newArrayList();
		// 单个匹配 match,字段需要分词
		MatchQueryBuilder builder1 = QueryBuilders.matchQuery("name", entity.getName());
		// 多个匹配 multi_match,字段需要分词
		MultiMatchQueryBuilder builder2 = QueryBuilders.multiMatchQuery(entity.getName(), "name", "title");
		// 精确查询一个 term,字段不能分词
		TermQueryBuilder builder3 = QueryBuilders.termQuery("id_card", entity.getIdCard());
		// 精确查询多个 terms,字段不能分词
		TermsQueryBuilder builder4 = QueryBuilders.termsQuery("id_card", entity.getIdCard(), "410184199510043582");
		// 范围查询  age > 12 and age <= 30
		RangeQueryBuilder builder5 = QueryBuilders.rangeQuery("age").from(12, Boolean.FALSE).to(30, Boolean.TRUE);
		// 短语匹配 phrase_query,字段不能分词
		MatchPhraseQueryBuilder builder6 = QueryBuilders.matchPhraseQuery("name", entity.getName());
		// 模糊查询 wild_card_query,类型为 keyword 通配符查询 * 0到多个 ? 匹配一个
		WildcardQueryBuilder builder7 = QueryBuilders.wildcardQuery("address", "*街道*");
		// 前缀匹配 prefix_query
		PrefixQueryBuilder builder8 = QueryBuilders.prefixQuery("name", "王*");
		// 多条件查询 bool 查询
		BoolQueryBuilder builder9 = QueryBuilders.boolQuery().should(builder2).should(builder3).must(builder4).mustNot(builder5).minimumShouldMatch(4);
		// fuzzy_query 模糊查询默认的匹配度是0.5，当这个值越小时，通过模糊查找出的文档的匹配程度就越低，查出的文档量就越多,反之亦然
		// 第一个参数当然是词条对象，第二个参数指的是levenshtein算法的最小相似度，第三个参数指的是要有多少个前缀字母完全匹配：
		FuzzyQueryBuilder builder10 = QueryBuilders.fuzzyQuery("company", entity.getCompany()).prefixLength(3).fuzziness(Fuzziness.ONE);
		// ids query
		IdsQueryBuilder builder11 = QueryBuilders.idsQuery().addIds("1", "2", "3");
		// query string
		QueryStringQueryBuilder builder12 = QueryBuilders.queryStringQuery("时间").field("name", 0.5f).defaultOperator(Operator.AND);
		// 避免相关性算分 constant_score
		ConstantScoreQueryBuilder builder13 = QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("name", "kimchy")).boost(2.0f);
		// dis_max_query 对子查询的结果做union, score沿用子查询score的最大值
		DisMaxQueryBuilder builder14 = QueryBuilders.disMaxQuery().add(builder6).add(builder7).boost(1.4F).tieBreaker(0.7F);
		// bool filter
		BoolQueryBuilder builder15 = QueryBuilders.boolQuery().filter(QueryBuilders.rangeQuery("salary").from(BigDecimal.valueOf(300)));
		
		// 分页查询数据
		NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(builder1)
				.setPageable(PageRequest.of(1, 10, Sort.by(Sort.Order.asc("_id"), Sort.Order.desc("name"))));
		
		
		SearchHits<PersonProfile> search = template.search(nativeSearchQuery, PersonProfile.class);
		
		for (int i = 0, len = (int)search.getTotalHits(); i < len; i++) {
			org.springframework.data.elasticsearch.core.SearchHit<PersonProfile> searchHit = search.getSearchHit(i);
			PersonProfile content = searchHit.getContent();
			log.info("entity is {}",content);
		}
		
		// source builder 分页 排序
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
				.query(builder15).sort("name", SortOrder.ASC)
				.profile(Boolean.TRUE)
				.explain(Boolean.TRUE)
				.from(10).size(10);
		
		SearchRequest searchRequest = new SearchRequest().source(sourceBuilder);
		
		// 查询数据
		SearchResponse search1 = client.search(searchRequest, RequestOptions.DEFAULT);
		// 查询结果
		SearchHit[] hits = search1.getHits().getHits();
		for (SearchHit node : hits) {
			log.info("{}", node);
		}
		return result;
	}
	
	
}
