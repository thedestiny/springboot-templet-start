# 工程简介



# 延伸阅读


``` 
查看所有索引
GET /_cat/indices



"track_total_hits":true,//开启精确查询，没有此字段，total最大10000条

启动 es 
/bin/elasticsearch.bat
启动 kibana 
/bin/kibana.bat 

http://localhost:5601

grok debugger 语法


ES 社区

https://elasticsearch.cn/download/

es 操作增删改查
https://blog.csdn.net/JAVA_MHH/article/details/122172506

field type elasticsearch 与 java 字段类型匹配
https://blog.csdn.net/aben_sky/article/details/121515175

elasticsearch 与 springboot 版本问题
https://blog.csdn.net/qq_36963950/article/details/127170085

```


```
 xpack.security.enabled: false

```


```
删除索引
DELETE user_index
查看索引
GET /user_index

PUT /user_index
{

  "settings":{
    "number_of_shards":1,
    "number_of_replicas":1,
    "index.refresh_interval": "120s",
    "index.translog.durability": "async",
    "index.translog.sync_interval": "120s",
    "index.translog.flush_threshold_size": "2048mb"
  },
  "mappings":{
    "properties":{
      "id":{
        "type":"long"
      },
      "username":{
        "type":"text"
      },
      "address":{
        "type":"text"
      },
      "email":{
        "type":"text"
      },
      "cellphone":{
        "type":"text"
      },
      "company":{
        "type":"text"
      },
      "age":{
        "type":"integer"
      },
      "weight":{
        "type":"double"
      },
      "sex":{
        "type":"text"
      },
      "idCard":{
        "type":"text"
      },
      "card":{
        "type":"text"
      },
      "university":{
        "type":"keyword",
        "index":false
      },
      "createTime":{
        "type":"date",
        "format":"yyyy-MM-dd HH:mm:ss"
      },
      "birthday":{
        "type":"date",
        "format":"yyyy-MM-dd HH:mm:ss"
      },
      "updateTime":{
        "type":"date",
        "format":"yyyy-MM-dd HH:mm:ss"
      }
    }
  }
}

```