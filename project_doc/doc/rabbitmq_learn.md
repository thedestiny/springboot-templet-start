

Direct exchange、Fanout exchange、Topic exchange、Headers exchange

##### Direct exchange 交换机
```
处理路由键。需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “abc”，
则只有被标记为“abc”的消息才被转发，不会转发abc.def，也不会转发dog.ghi，只会转发abc。
```
##### Fanout Exchange 交换机
```
不处理路由键。你只需要简单的将队列绑定到交换机上。一个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上。很像子网广播，每台子网内的主机都获得
了一份复制的消息。Fanout交换机转发消息是最快的。类似于广播消息
```

##### Topic Exchange 交换机
```
将路由键和某种模式进行匹配。此时队列需要绑定要一个模式上。符号“#”匹配一个或多个词，符号“”匹配不多不少一个词。因此“abc.#”能够匹配到“abc.def.ghi”，
但是“abc.” 只会匹配到“abc.def”。
```

##### Headers Exchanges
```
不处理路由键。而是根据发送的消息内容中的headers属性进行匹配。在绑定Queue与Exchange时指定一组键值对；当消息发送到RabbitMQ时会取到该消息的headers
与Exchange绑定时指定的键值对进行匹配；如果完全匹配则消息会路由到该队列，否则不会路由到该队列。headers属性是一个键值对，可以是Hashtable，键值对的
值可以是任何类型。而fanout，direct，topic 的路由键都需要要字符串形式的。
```

生产者 消费者 消息 
交换器 队列 绑定 路由键 
路由键 绑定键 最长255个字符
porducer routekey exchange queue consumer 
消费者收到的每一条消息都需要确认（手动确认或者自动确认）

虚拟主机 类似于 tomcat host 
vhost 
```
# 号匹配多个.  mark.#   mark.value.1
* 只匹配一个   *.info   jvm.info 

```

解耦 异步处理 削峰填谷
伸缩 扩展 

一个连接 tcp/ip  多个信道 
一个队列 多个消费者 

队列超过最大长度 且 拒绝消费 且 requeue = false 


    消息被拒绝(basic.reject / basic.nack)，并且requeue = false
    消息TTL过期
    队列达到最大长度

队列参数 args 
x-message-ttl  消息的ttl
x-max-length  队列的最大长度
x-max-length-bytes 消息的最大字节数
x-overflow 溢出时处理方式 可能的值是 drop-head (默认)或 reject-publish 删除头部或者拒绝发布
```
Map<String, Object> args = new HashMap<String, Object>();
args.put("x-max-length", 10);
channel.queueDeclare("myqueue", false, false, false, args);
```


##### 确保消息不丢失
```
1 确保消息到MQ,采用发送方的确认模式
2 确保消息路由到正确的队列 路由失败通知
3 确保消息在队里能够给存储
4 确保消息能从队列中正确的投递到消费者 手动确认-> 交给消费者进行确认
```
