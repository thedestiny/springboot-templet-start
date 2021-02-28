nacos 的配置优先级
精准配置 > 不同环境的通用配置 > 不同工程的 ext-config > 不同工程的 shared-dataids 


```


spring:
    cloud:
      nacos:
        config:
          server‐addr: localhost:8848
          file‐extension: yml
          shared‐dataids: common.yml,common2.yml
          refreshable‐dataids: common.yml,common2.yml
          ext‐config:
              ‐ data‐id: common3.yml
                  group: DEFAULT_GROUP
                  refresh: true
              ‐ data‐id: common4.yml
                group: DEFAULT_GROUP
                refresh: true
    application:
      name: order‐center
    profiles:
       active: dev


#  1 order-center-dev.yml 精准配置
#  2 order-center.yml 同工程不同环境的通用配置
#  3 ext-config: 不同工程 通用配置
#    3.1)：common4.yml
#   3.2): common3.yml
#  4) shared-dataids 不同工程通用配置
#    4.1)common2.yml
#    4.2)common1.yml


```
