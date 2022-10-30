爬虫利器
http://www.jsphp.net/python/show-24-214-1.html
https://cuiqingcai.com/1319.html



```
### python 依赖安装地址

https://pypi.mirrors.ustc.edu.cn/simple/

# 导出当前环境的依赖
pip freeze > requirements.txt
# 按照依赖进行安装数据
pip install -r requirements.txt


BeautifulSoup find find_one find_all

select select_one select_all

# 根据属性查询数据
goto = soup.find(attrs={"name": "goto"}).attrs["value"]
SunQueryParamsString = soup.find(attrs={"name": "SunQueryParamsString"}).attrs["value"]
title = soup.find("h4", class_="h9 m5").text
  
  
# 根据标签和属性进行查询数据
soup.find("h4", class_="h9 m5").text

接受索引和 child
for i, ch in enumerate(soup.p.descendants):


soup.a.parent 

soup.a.next_siblings 后面的兄弟节点
soup.a.previous_siblings  前面的兄弟节点

find_all( name , attrs , recursive , text , **kwargs )


```