package com.platform.fund.task;

import com.platform.fund.service.EtfListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Date 2023-01-09 3:35 PM
 */

@Slf4j
@Component
public class AppEtfTask {

    @Autowired
    private EtfListService listService;

    /**
     * 定时任务
     */
    // @Scheduled(cron = "*/5 * * * * ?")
    public void task01(){

        // listService.updateInnerEtfCodeListAuto();
        listService.updateInnerEtfCodeWebAuto();

    }





}
