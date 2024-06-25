package com.destiny.origin.example;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class RocketMqExample {


    private String NAME_SERVER_ADDR = "192.168.100.84:9876";

    /**
     * 异步消息测试 发送异步消息
     * 异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。发送完以后会有一个异步消息通知。
     */
    public void simpleAsyncProducer() throws Exception {
        //创建一个生产者，并指定一个组名
        DefaultMQProducer producer = new DefaultMQProducer("async-producer-group");
        //连接namesrv,参数是namesrv的ip地址:端口号
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        //启动
        producer.start();
        //指定topic，创建一个消息
        Message message = new Message("asyncTopic1", "这是一条异步消息".getBytes());
        //发送异步消息，并设置回调内容
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("回调内容，发送成功");
            }
            @Override
            public void onException(Throwable throwable) {
                log.info("回调内容，发送失败");
            }
        });
        log.info("主线程执行中=========");
        System.in.read();
    }


    /**
     * 发送单向消息
     * 这种方式主要用在不关心发送结果的场景，这种方式吞吐量很大，但是存在消息丢失的风险，例如日志信息的发送。
     */
    public void oneWayMessageTest() throws Exception {
        //创建一个生产者，并指定一个组名
        DefaultMQProducer producer = new DefaultMQProducer("oneway-producer-group");
        //连接namesrv,参数是namesrv的ip地址:端口号
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        //启动
        producer.start();
        //指定topic，创建一个消息
        Message message = new Message("onewayTopic1", "这是一条单向消息".getBytes());
        //发送单向消息
        producer.sendOneway(message);
        producer.shutdown();
    }

    /**
     * 发送延迟消息
     消息放入mq后，过一段时间，才会被监听到，然后消费.
     比如下订单业务，提交了一个订单就可以发送一个延时消息，30min后去检查这个订单的状态，如果还是未付款就取消订单释放库存。
     */
    public void msMessageTest() throws Exception{
        //创建一个生产者，并指定一个组名
        DefaultMQProducer producer = new DefaultMQProducer("ms-producer-group");
        //连接namesrv,参数是namesrv的ip地址:端口号
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        //启动
        producer.start();
        //指定topic，创建一个消息
        Message message = new Message("msTopic1", "这是一条单向消息".getBytes());
        //给消息设置一个延迟时间
        message.setDelayTimeLevel(3);
        //发送延时消息
        producer.sendOneway(message);
        producer.shutdown();
    }

    /**
     *
     * @throws Exception
     */
    public void testBatchProducer() throws Exception {
        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("test-batch-group");
        //连接namesrv,参数是namesrv的ip地址:端口号
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        // 启动实例
        producer.start();
        List<Message> msgs = Arrays.asList(
                new Message("batchTopicTest", "我是一组消息的A消息".getBytes()),
                new Message("batchTopicTest", "我是一组消息的B消息".getBytes()),
                new Message("batchTopicTest", "我是一组消息的C消息".getBytes())

        );
        SendResult send = producer.send(msgs);
        System.out.println(send);
        // 关闭实例
        producer.shutdown();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageModel {
        //订单id
        private String orderId;
        //用户id
        private String userId;
        //消息描述
        private String description;
    }


    /**
     * 顺序消息
     * 可以想象一个场景，我们在网上购物时，需要先完成下订单操作，然后再去发短信，再进行发货，需要保证顺序的。
     * 前文我们讲的都是并发消息，这种消息并不能完成上述的场景逻辑。比如一个topic里有10个消息，分别在4个队列中；
     * 如果消费者，同时有20个线程在消费，可能A线程拿到消息1了，B线程拿到消息2了，但是B线程可能完成的比A线程早，这就没办法上述场景的顺序了。
     * 如果消费者只有一个线程，轮询消费四个队列中的消息时，也不能保证是网购场景中的顺序的。
     * 这就要引出顺序消息：把消费者变为单线程，把下订单消息、发短信消息、发货消息放到同一个队列就可以了。

     */

    public void testOrderlyProducer() throws Exception {

        List<MessageModel> messageModelList = Arrays.asList(
                //用户1的订单
                new MessageModel("order-111","user-1","下单"),
                new MessageModel("order-111","user-1","发短信"),
                new MessageModel("order-111","user-1","发货"),

                //用户2的订单
                new MessageModel("order-222","user-2","下单"),
                new MessageModel("order-222","user-2","发短信"),
                new MessageModel("order-222","user-2","发货")
        );

        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("test-orderly-group");
        //连接namesrv,参数是namesrv的ip地址:端口号
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        // 启动实例
        producer.start();

        //发送顺序消息时 发送时相同用户的消息要保证有序，并且发到同一个队列里
        messageModelList.forEach(
                messageModel->{
                    Message message = new Message("orderlyTopic", messageModel.toString().getBytes());
                    try {
                        //发送消息，相同订单号去相同队列
                        producer.send(message, new MessageQueueSelector() {
                            @Override
                            public MessageQueue select(List<MessageQueue> mqs, Message message, Object arg) {
                                //producer.send(message,selector,arg),第三个参数订单号会传给selector要实现的方法的arg
                                //在这里选择队列
                                int hashCode = Math.abs(arg.toString().hashCode());
                                int index = hashCode % mqs.size();
                                return mqs.get(index);
                            }
                        }, messageModel.getOrderId());
                    } catch (Exception e) {
                        log.error("有错误发生",e);
                    }
                }
        );
        // 关闭实例
        producer.shutdown();
        log.info("发送完成");
    }

    //消费者
    public void orderlyConsumer() throws Exception {
        //创建一个消费者，并指定一个组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-orderly-consumer-group");
        //连接namesrv,参数是namesrv的ip地址:端口号
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        //订阅一个主题 *号表示订阅这个主题中所有的消息
        consumer.subscribe("orderlyTopic","*");
        //设置一个监听器（一直监听，异步回调方式）
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                log.info("线程id"+Thread.currentThread().getId());
                log.info("消息内容:"+new String(msgs.get(0).getBody()));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        //启动消费者
        consumer.start();

        //挂起当前jvm,防止主线程结束，让监听器一直监听
        System.in.read();

    }

    /**
     * 如果我们有衣服订单的消息、手机订单的消息，如果我们只使用topic进行区分，就要使用两个topic；但是它们都是订单，所以在同一个topic中会好一些，Rocketmq就提供了消息过滤功能，通过tag或者key进行区分。
     */
    public void testTagProducer() throws Exception {
        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("test-tag-group");
        //连接namesrv,参数是namesrv的ip地址:端口号
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        // 启动实例
        producer.start();
        Message messageTopic1 = new Message("tagTopic", "tag1", "这是topic1的消息".getBytes());
        Message messageTopic2 = new Message("tagTopic", "tag2", "这是topic2的消息".getBytes());
        producer.send(messageTopic1);
        producer.send(messageTopic2);
        // 关闭实例
        producer.shutdown();
    }

    //消费tag1的消费者
    public void tagConsumer1() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tag-consumer-group-a");
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        consumer.subscribe("tagTopic", "tag1");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println("我是tag1的消费者，我正在消费消息" + new String(msgs.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }

    //消费tag1和tag2的消费者
    public void tagConsumer2() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tag-consumer-group-a");
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        consumer.subscribe("tagTopic", "tag1 || tag2");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println("我是tag1和tag2的消费者，我正在消费消息" + new String(msgs.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }

    public void testKeyProducer() throws Exception {
        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("test-key-group");
        //连接namesrv,参数是namesrv的ip地址:端口号
        producer.setNamesrvAddr(NAME_SERVER_ADDR);

        String key = UUID.randomUUID().toString();

        // 启动实例
        producer.start();
        Message messageTopic1 = new Message("keyTopic", "tag1",key, "这是topic1的消息".getBytes());
        producer.send(messageTopic1);
        // 关闭实例
        producer.shutdown();
    }

    public void testKeyConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("key-consumer-group-a");
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        consumer.subscribe("keyTopic","*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println("我们设置的key：" + msgs.get(0).getKeys());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }



}
