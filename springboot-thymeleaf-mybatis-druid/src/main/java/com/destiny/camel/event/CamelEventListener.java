package com.destiny.camel.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CamelEventListener {
	
	
	@EventListener
	public void messageEvent(MessageEvent event) {
		
		CompletableFuture.runAsync(() -> {
			String msg = event.getMsg();
			Object source = event.getSource();
			long timestamp = event.getTimestamp();
			log.info("msg is {} and source is {} and timestamp is {}", msg, source, timestamp);
			
		});
	}
	
	
}
