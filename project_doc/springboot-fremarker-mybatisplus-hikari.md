###### Feature  mybatis的xml查询sql参数以mapper中实际参数名进行查询
```
配置步骤

1 mybatis 或者 mybatis-plus 开启参数 use-actual-param-name: true
2 打包项目时需要添加 -parameters 参数,pom.xml配置如下:
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.0</version>
                <configuration>
                    <compilerArgument>-parameters</compilerArgument>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
3 mapper 参数中配置参数如下
	接口文件中调用如下
	/** 测试根据原始参数名称进行查询 */
	User selectUserListByNameAndAge(String username, Integer age);
	xml 文件中入下
    <select id="selectUserListByNameAndAge" resultMap="user_base_result_map">
        select
        <include refid="sql_columns"/>
        from `tb_user`
        where username = #{username} and age = #{age} limit 1
    </select>

4 原理
mybatis3.4 以后开启此功功能,要配合jdk8使用
默认情况下mybatis中的参数传递默认按照顺序进行赋值 #{}为arg0 - #{}为argn 或者#{}为paramn1 - #{}paramnn
或者在接口层采用@Param注解对参数起名
jdk8 打包加上 -parameters 在字节码层会保留代码层的参数名，这样参数名就可以保留下来,而不是var1,var2

```


