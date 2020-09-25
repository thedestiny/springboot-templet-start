package com.destiny.wolf.util;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

public class ESSyntaxTest {
	
	public static void main(String[] args) {
		
		/**
		 {
		 "range" : {
		 "age" : {
		 "from" : 10,
		 "to" : 20,
		 "include_lower" : true,
		 "include_upper" : true,
		 "boost" : 1.2
		 }
		 }
		 }
		 * */
		RangeQueryBuilder to = QueryBuilders.rangeQuery("age").from(10).to(20).boost(Float.valueOf("1.2"));
		RangeQueryBuilder t1 = QueryBuilders.rangeQuery("weight").gt(30).boost(Float.valueOf("1.2"));
		MatchQueryBuilder t3 = QueryBuilders.matchQuery("address","河东");
		PrefixQueryBuilder t4 = QueryBuilders.prefixQuery("idcard", "410");
		TermsQueryBuilder t5 = QueryBuilders.termsQuery("hobby", "跑步","看书");
		
		BoolQueryBuilder must = QueryBuilders.boolQuery().must(to).should(t1).mustNot(t3).must(t4).should(t5);
		// System.out.println("must : \n" + must);
		
		
		System.out.println(to);
		// System.out.println(t1);
		NativeSearchQuery searchQuery1 = new NativeSearchQueryBuilder()
				.withQuery(must)
				.build();
		System.out.println(searchQuery1);
		QueryStringQueryBuilder builder = QueryBuilders.queryStringQuery("开发开放").defaultField("title");
		
		/**
		 {
		 "query_string" : {
		 "query" : "开发开放",
		 "default_field" : "title",
		 "fields" : [ ],
		 "type" : "best_fields",
		 "default_operator" : "or",
		 "max_determinized_states" : 10000,
		 "enable_position_increments" : true,
		 "fuzziness" : "AUTO",
		 "fuzzy_prefix_length" : 0,
		 "fuzzy_max_expansions" : 50,
		 "phrase_slop" : 0,
		 "escape" : false,
		 "auto_generate_synonyms_phrase_query" : true,
		 "fuzzy_transpositions" : true,
		 "boost" : 1.0
		 }
		 }
		 *
		 * */
		
		System.out.println(builder);
		
	}
	
	
}
