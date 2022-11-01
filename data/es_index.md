# PUT /career_plan_sku_index 创建 ES 索引
# 设置分片和副本数
# ik_max_word ik 分词器分成多个组合  ik_smart 智慧拆分
# refresh_interval 刷新频率

elasticsearch  
https://juejin.cn/post/7157377084560506887

ES 写入数据优化
1 批量上传数据
2 使用多线程写入数据
3 设置数据刷新间隔
4 将初始副本数量设置为0，方便数据的写入
5 关闭交换区
6 给数据以足够的内存空间
7 使用自己生成的id
8 使用高性能的云盘
9 索引缓冲大小，默认为 10% 
10 translog 文件存在于内存中，如果断电则数据丢失 segment  flush async 


```json
{

  "settings":{
    "number_of_shards":3,
    "number_of_replicas":1,
    "index.refresh_interval": "120s",
    "index.translog.durability": "async",
    "index.translog.sync_interval": "120s",
    "index.translog.flush_threshold_size": "2048mb"
  },
  "mappings":{
    "properties":{
      "skuId":{
        "type":"keyword"
      },
      "skuName":{
        "type":"text",
        "analyzer":"ik_max_word",
        "search_analyzer":"ik_smart"
      },
      "category":{
        "type":"keyword"
      },
      "basePrice":{
        "type":"integer"
      },
      "vipPrice":{
        "type":"integer"
      },
      "saleCount":{
        "type":"integer"
      },
      "commentCount":{
        "type":"integer"
      },
      "skuImgUrl":{
        "type":"keyword",
        "index":false
      },
      "createTime":{
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