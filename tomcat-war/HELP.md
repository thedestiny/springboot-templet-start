
依次点击setting --> Build，Execution，Deployment --> Annotation Processors打开编辑页面，
勾上 Enable annotation processing前面的勾，保存后需要重启IDEA

```
字符串截取， 
string 字符串
sep 分隔符
num 获取第几个内容
substring_index(string, sep, num)

# cast 用于转换数据类型
13d 2022-02-01
select cast(substring_index(ctime,' ',1) as date) as dt,
```
