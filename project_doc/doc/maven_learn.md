###### maven 
```xml
<mirrors>
   <mirror>  
      <id>alimaven</id>  
      <name>aliyun maven</name>  
      <url>https://maven.aliyun.com/repository/public/</url>  
      <mirrorOf>central</mirrorOf>          
    </mirror>
    <mirror>
      <id>repo2</id>
      <mirrorOf>central</mirrorOf>
      <name>Human Readable Name for this Mirror.</name>
      <url>http://repo2.maven.org/maven2/</url>
    </mirror>
    <mirror>
      <id>ui</id>
      <mirrorOf>central</mirrorOf>
      <name>中央仓库描述</name>
      <url>http://uk.maven.org/maven2/</url>
    </mirror>
</mirrors>


```

单项目配置
Maven默认中央仓库的id 为 central。id是唯一的。因此使用< id>central< /id>覆盖了默认的中央仓库
```xml

<repositories>
        <repository>
            <id>central</id>
            <name>aliyun maven</name>
            <url>https://maven.aliyun.com/repository/public/</url>
            <layout>default</layout>
            <!-- 是否开启发布版构件下载 -->
            <releases>
                <enabled>true</enabled>
            </releases>
            <!-- 是否开启快照版构件下载 -->
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>
```
