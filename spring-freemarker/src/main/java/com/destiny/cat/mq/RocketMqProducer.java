package com.destiny.cat.mq;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

@Slf4j
public class RocketMqProducer {
	
	public static DefaultMQProducer producer = null;
	
	public static void init() {
		producer = new DefaultMQProducer("rocketmq001");
		producer.setNamesrvAddr("123.57.63.12:9876");
		try {
			producer.start();
		} catch (MQClientException e) {
		
		}
	}
	
	/**
	 * rocketmq发送消息方法
	 *
	 * @param topic   组名
	 * @param tagName 同一个topic下的不同 分支,同一个消费者只能取一个组的下的不同的tag分支
	 * @param key     保持唯一
	 * @return
	 * @author TF013242 2018-1-20 下午2:32:19
	 */
	public static void sendMsgIntime(String topic, String tagName, String key, byte[] msgBody) {
		Message msg = new Message(topic, tagName, key, msgBody);
		// msg.setDelayTimeLevel(3);
		//set to broadcast mode
		// consumer.setMessageModel(MessageModel.BROADCASTING);
		try {
			
			
			// 同步发送模式
			String result = producer.send(msg).toString();
			// producer.sendOneway();
			
			
			log.info("send rockmq topic:" + topic + " " + result);
		} catch (Exception e) {
			log.error("send rockmq error topic:" + topic + String.valueOf(msg), e);
		}
	}
	
	public static void main(String[] args) {
		
		init();
		for (int i = 0; i < 100; i++) {
			String s = IdUtil.randomUUID();
			
			sendMsgIntime("topic001", "tag001", s, "dddddd中文".getBytes());
		}
		
		
	}
	
}
