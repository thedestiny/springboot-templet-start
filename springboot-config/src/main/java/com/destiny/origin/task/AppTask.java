package com.destiny.origin.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@EnableScheduling
public class AppTask {

    // 这里的单位是毫秒 每隔 100s 执行一次
    @Scheduled(fixedRate = 100000)
    public void taskFixed() {
        String now = DateUtil.date().toString("yyyy-MM-dd HH:mm:ss.SSS");
        log.info("{} task execute taskFixed", now);
    }

    // cron 表达式，设置cron 表达式的工具有很多，https://www.bejson.com/othertools/cron/
    // 这里需要说明一下这个 cron 表达式是可以精确到秒，但是的linux 系统中，crontab 设置定时任务时一般是精确到分钟
    @Scheduled(cron = "3 4/3 * * * ? ")
    public void taskCron() {
        String now = DateUtil.date().toString("yyyy-MM-dd HH:mm:ss.SSS");
        log.info("{} task execute taskCron", now);
    }

}
