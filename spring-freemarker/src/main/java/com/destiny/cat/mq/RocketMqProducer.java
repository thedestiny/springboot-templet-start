package com.destiny.cat.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

@Slf4j
public class RocketMqProducer {
	
	public static DefaultMQProducer producer = null;
	
	public static void init(){
		producer = new DefaultMQProducer("rocketmq001");
		producer.setNamesrvAddr("127.0.0.1:9876");
		try {
			producer.start();
		} catch (MQClientException e) {
		
		}
	}
	
	/**
	 * rocketmq发送消息方法
	 *
	 * @author TF013242 2018-1-20 下午2:32:19
	 * @param topic 组名
	 * @param tagName 同一个topic下的不同 分支,同一个消费者只能取一个组的下的不同的tag分支
	 * @param key 保持唯一
	 * @return
	 */
	public static void sendMsgIntime(String topic, String tagName, String key, byte[] msgBody) {
		Message msg = new Message(topic,tagName,key,msgBody);
		try {
			String result = producer.send(msg).toString();
			log.info("send rockmq topic:" + topic + " " + result);
		} catch (Exception e) {
			log.error("send rockmq error topic:" + topic + String.valueOf(msg), e);
		}
	}
}
