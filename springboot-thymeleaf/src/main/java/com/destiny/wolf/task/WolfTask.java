package com.destiny.wolf.task;


import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WolfTask {
	
	
	@Scheduled(cron = "10/20 * * * * ?")
	public void task001(){
		
		DateTime dateTime = new DateTime();
		log.info("task execute, and time is {}",dateTime.toString());
		
	}



}
