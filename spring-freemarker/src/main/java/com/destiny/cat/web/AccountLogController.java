package com.destiny.cat.web;


import com.alibaba.fastjson.JSONObject;

import com.destiny.cat.config.Result;
import com.destiny.cat.dto.LogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 账户流水表 前端控制器
 * </p>
 *
 * @author kaiyang
 * @since 2023-05-29
 */
@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountLogController {


    @GetMapping(value = "log")
    public Result<LogDto> queryLog(){
        LogDto dto = new LogDto();
        dto.setName("张晓明");
        dto.setIdCard("422024199112250937");
        dto.setCellphone("12384957782");
        dto.setMoney("23.8");
        dto.setRemark("测试内容");
        log.info("log is {}", JSONObject.toJSONString(dto));
        Result ok = Result.ok();
        ok.setData(dto);

        return ok;


    }



}
