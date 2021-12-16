package com.destiny.hedghog.config;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;


/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-11-29 2:24 PM
 */

@Configuration
public class RabbitMqConfig {

    // 交换机名称和队列名称
    public static final String DIRECT_EXCHANGE_NAME = "direct_exchange_name";
    public static final String DIRECT_QUEUE_NAME = "direct_queue_name";



    @Bean("directExchange")
    public Exchange directExchange(){
       return ExchangeBuilder.directExchange(DIRECT_EXCHANGE_NAME).durable(true).build();
    }
    @Bean("directQueue")
    public Queue directQueue(){
        return QueueBuilder.durable(DIRECT_QUEUE_NAME).build();
    }


    @Bean
    public Binding bindExchangeQueue(@Qualifier("directQueue") Queue queue,
                                     @Qualifier("directExchange") Exchange exchange ){

        return BindingBuilder.bind(queue).to(exchange).with("info").noargs();
    }

    public static void main(String[] args) {

        LinkedList<String> lst = new LinkedList<String>();
        lst.add("dd");
        String poll = lst.poll();



    }


}
