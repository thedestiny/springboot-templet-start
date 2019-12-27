检查集群健康状况 GET _cat/health?v
查询集群节点 GET _cat/nodes?v
查看集群索引 GET _cat/indices?v
创建索引  PUT /customer?pretty
插入数据 PUT /customer/test/1?pretty 
{
  "name":"小明",
  "age":32
}
查询数据 GET /customer/test/1?pretty 

删除索引 DELETE /demo?pretty

更新数据
POST /customer/test/1/_update?pretty 
{
  "doc":{
      "weight":23.9
  }
}
删除索引
DELETE /customer/test/1?pretty


curl -X<REST Verb> <Node>:<Port>/<Index>/<Type>/<ID>
　　<REST Verb>：REST风格的语法谓词
　　<Node>:节点ip
　　<port>:节点端口号，默认9200
　　<Index>:索引名
　　<Type>:索引类型
　　<ID>:操作对象的ID号

查询姓名包含小的
POST  /customer/test/_search?pretty 
{
  "query": {"match":{"name":"小"}}
}