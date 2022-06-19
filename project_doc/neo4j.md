
neo4j 以图论为基础进行数据的关系存储，非关系型数据库

node 一个实体记录，像关系型数据库中的一条记录，一个节点包含多个属性和标签


create 新增命令
创建没有属性的节点
create (a: A{})
创建有属性的节点
CREATE (dept: Dept{ deptno:10,dname:"Accounting",location:"Hyderabad" })

match 查找命令
无条件查询
match {a:A} return a 

有条件查询节点：
match (a:A{p1:v1,p2:v2 … pn:vn}) return a 等同于
match (a:A) where a.p1 = v1 and a.p2 = v2 and … and a.pn = vn return a
模糊查询：
match (a:A) where a.p =~ '. v .*’ return a* (p是属性，v是值，String类型的值要用双引号括起来)
所有查询必须配合return子句一起使用才能返回结果。

set 修改命令
match (a:A {p1:v1}) set a.p2 = v2 , … ,pn = vn return a 等同于
match (a:A) where a.p1 = v1 set a.p2 = v2, … , pn = vn return a

delete命令（删除节点和关系）

删除节点： match (a:A) delete a
有条件删除节点：match (a:A {p1:v1}) delete a 等同于 match (a:A) where a.p1 = v1 delete a
删除所有节点包括标签：match (a:A) detach delete a
删除节点和关系：match (a:A) - [r] -> [b:B] delete a,r,b
删除节点的关系：match (a:A) - [r] -> [b:B] delete r
删除所有节点和关系：match (n) optional match (n) - [r] - () delete n,r
remove命令（删除标签和属性）

无条件删除节点的属性（类似删除实体类的字段）：match （a:A) remove a.p1,a.p2, … ,a.pn
有条件删除节点的属性：match （a:A {p1:v1}) remove a.p2
等同于 match (a:A) where a.p1 = v1 remove a.p2
删除标签：match (a:A) remove a:L （使用情况较少，建议使用detach delete）
match (a:A) detach delete a (会删除标签A和所有A下面的节点)

https://blog.csdn.net/guangdongshinian/article/details/115697030

neo4j图数据库中基本元素与概念

节点(node)
表示一个实体记录、就像关系数据库当中一条记录。 一个节点包含多个属性和标签。
关系(relationship)
关系用于将节点关联起来构成图，关系也称为图论的边(Edge)。
属性(property)
节点和关系都可以有多个属性。属性是由键值对组成的，就像JAVA当中哈希。
标签(Label)
标签指示一组拥有相同属性的节点，但不强制要求相同，一个节点可以有多个标签。
路径(path)
图中任意两个节点都存在由关系组成的路径,路径有长短之分。

