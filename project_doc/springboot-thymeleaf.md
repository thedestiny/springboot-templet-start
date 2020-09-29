
```

# redisson 官方文档
https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95


关闭swapping 
/etc/security/limits.conf
ulimit -n 65535
sysctl -w vm.max_map_count=262144

最佳实践
禁用 dynamic 

/_nodes/stas

GET /_cat/indices
GET /_cat/health
GET /_nodes/stats
GET /_cluster/state

# 索引添加字段
PUT my_index/_mapping/products?update_all_types
{
  "properties": {
      "tag": {
        "type": "text"
      }
  }
}


# match_phrase
GET /my_index/_doc/_search
{
  "query":{
     "match_phrase":{
        "name_field":"查询内容"
     }
  }
}

# query_string
GET /my_index/_doc/_search
{
  "query":{
     "query_string":{
        "query":"查询内容",
        "fields":["field1","field2","field3"]
     }
  }
}


constant_score的用处
当我们不关心检索词频率TF(Term Frequency)对搜索结果排序的影响时,可以使用constant_score将查询语句query或者过滤语句filter包装起来。
检索词频率：检索词在该字段出现的频率。出现频率越高,相关性也越高。字段中出现过5次要比只出现过1次的相关性高。
反向文档频率IDF
反向文档频率：每个检索词在索引中出现的频率。频率越高，相关性越低。 检索词出现在多数文档中会比出现在少数文档中的权重更低， 即检验一个检索词在文档中的普遍重要性。


Query DSL (Domain Specific Language)，基于json的查询方式
DSL查询语言中存在两种：查询DSL（query DSL）和过滤DSL（filter DSL）
query DSL 
1 全文搜索匹配度
2 包含单词，已经动词、形容词、副词
3 


filter DSL


{ 
    "filtered": { 
        "query":  { "match": { "email": "business opportunity" }}, 
        "filter": { "term":  { "folder": "inbox" }} 
    } 
}

GET /_search 
{ 
    "query": { 
        "filtered": { 
            "query":  { "match": { "email": "business opportunity" }}, 
            "filter": { "term": { "folder": "inbox" }} 
        } 
    } 
} 

```
