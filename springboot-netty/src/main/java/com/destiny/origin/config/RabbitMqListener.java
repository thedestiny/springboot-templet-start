package com.destiny.origin.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description
 * @Author destiny
 * @Date 2021-11-29 10:19 AM
 */
// https://www.cnblogs.com/gyjx2016/p/13704504.html
// https://blog.csdn.net/LoveLacie/article/details/105398970

@Slf4j
@Component
public class RabbitMqListener  {


    @RabbitListener(queues = "direct_message")
    public void testListener(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("testListener message {}",message);
        try {
            // deliveryTag 取出来的当前消息在队列中的索引
            // multiple  true 为批量确认 false 为单个确认

            channel.basicAck(tag,false);
        } catch (Exception e){

            channel.basicNack(tag,false,true);
        }


    }


}
