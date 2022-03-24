package com.destiny.origin.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class NoticeListener implements ApplicationListener<AppOriginEvent> {


    @Override
    public void onApplicationEvent(AppOriginEvent event) {
        log.info(" pub task event !");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("event thread id {} ", Thread.currentThread().getId());
        String message = event.getMessage();
        log.info(" message {}", message);

    }

}
