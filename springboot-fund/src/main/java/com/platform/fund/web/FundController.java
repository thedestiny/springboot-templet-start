package com.platform.fund.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.fund.dto.EtfPageReq;
import com.platform.fund.dto.Result;
import com.platform.fund.entity.EtfList;
import com.platform.fund.service.EtfListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Date 2023-01-09 1:01 PM
 */

@Slf4j
@RestController
@RequestMapping(value = "api/v1")
public class FundController {

    @Autowired
    private EtfListService listService;

    /**
     * 分页查询数据信息
     * localhost:9098/api/v1/etf/list
     * @param req
     * @return
     */
    @GetMapping(value = "etf/list")
    public  Result<Page<EtfList>>  queryEtfList(EtfPageReq req){

        Page<EtfList> page = listService.queryEtfListPage(req);
        Result<Page<EtfList>> result = new Result<>();
        result.setCode("200");
        result.setMsg("请求成功");
        result.setData(page);
        return result;
    }





}
