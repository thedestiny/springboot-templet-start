

``
批量插入数据
POST /book_index/_bulk
{ "index":{ "_index": "book_index", "_type": "_doc" } }
{ "title":"平凡的世界","author":"路遥","price":23.7 }
{ "index":{ "_index": "book_index", "_type": "_doc" } }
{ "title":"西游记","author":"吴承恩","price":33.7 }

term是代表完全匹配，也就是精确查询，搜索前不会再对搜索词进行分词，所以我们的搜索词必须是文档分词集合中的一个
TermsBuilder:构造聚合函数
AggregationBuilders:创建聚合函数工具类
BoolQueryBuilder:拼装连接(查询)条件
QueryBuilders:简单的静态工厂”导入静态”使用。主要作用是查询条件(关系),如区间\精确\多值等条件
NativeSearchQueryBuilder:将连接条件和聚合函数等组合
SearchQuery:生成查询
elasticsearchTemplate.query:进行查询
Aggregations:Represents a set of computed addAggregation.代表一组添加聚合函数统计后的数据
Bucket:满足某个条件(聚合)的文档集合

elasticsearchRestTemplate.queryForList是查询一个列表，用的就是ElasticsearchRestTemplate的一个对象实例；
NativeSearchQuery ：是spring data中的查询条件；
NativeSearchQueryBuilder ：用于建造一个NativeSearchQuery查询对象；
QueryBuilders ：设置查询条件，是ES中的类；
SortBuilders ：设置排序条件；
HighlightBuilder ：设置高亮显示；


term、terms 不支持分词
match/multimatch 支持分词
range 范围查询
https://blog.csdn.net/csdn_20150804/article/details/105618933


# 范围查询个人档案信息 select age,job from  person_profile where age => 20 and age <= 25 order by age asc , company desc 
GET /person_profile/_doc/_search 
{
  "query":{
    "range" : {
      "age" : {
          "from" : 20,
          "to" : 25,
          "include_lower" : true,
          "include_upper" : true,
          "boost" : 1.2
      }
    }
  },
  "from": 10 ,
  "size": 5 ,
  "sort": [
    {
     "age": {
        "order": "asc"
      }
    },
    {
     "company.keyword": {
        "order": "desc"
      }
    }
  ],
  "highlight":{
     "fields":{
        "phone":{}
     }
  },
  "_source":["age","job"]
}

# 根据id批量查询数据
GET /person_profile/_doc/_mget
{
  "ids":["1308647544217276771","1308647544217276844"]
}

# 精确查询 term
GET /person_profile/_doc/_search
{
  "query":{
    "term":{
       "age":20
    }
  }
}

使用 alias 创建不同查询的视图
POST /_alias
{
   "actions":[
      {
         "add":{
            "index":"movies",
             "alias":"high_rate_movies",
             "filter":{
                "range":{
                   "rating":{ "gte" : 4 }
                }
             }
         }
      }
   ]
}


POST /_reindex 
{
  "source":{
     "index":"blogs"
  },
  "dest":{
      "index":"blogs_fix",
      "op_type":"create"
  }

}



