package com.destiny.cat.mq;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.MessageQueueListener;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Set;

@Slf4j
public class RocketMqConsumer {
	
	public static void main(String[] args) throws Exception {
	
	
		pullConsumer();
	}
	
	
	private static void pullConsumer() throws Exception {
		
		DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("consumergroup001");
		consumer.setNamesrvAddr("192.168.0.105:9876");
		consumer.setInstanceName("consumer001");
		consumer.setPullBatchSize(2);
		consumer.start();
		
		consumer.subscribe("topic001", "*");
		consumer.setMessageQueueListener(new MessageQueueListener() {
			@Override
			public void messageQueueChanged(String topic, Set<MessageQueue> mqAll, Set<MessageQueue> mqDivided) {
				
				
				System.out.println("topic is " + topic);
				System.out.println(mqAll);
				mqAll.forEach(noe -> {
					System.out.println(noe);
				});
				
				
			}
		});
		
		
		consumer.shutdown();
		
		
	}
	
}
