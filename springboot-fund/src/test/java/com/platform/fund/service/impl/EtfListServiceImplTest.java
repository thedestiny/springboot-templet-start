package com.platform.fund.service.impl;

import com.platform.fund.FundApplicationTest;
import com.platform.fund.service.EtfListService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Date 2023-01-09 3:58 PM
 */

@Slf4j
@Component
public class EtfListServiceImplTest extends FundApplicationTest {

    @Autowired
    private EtfListService listService;


    @Test
    public void testUpdateFundRateInfoByCode() {


        listService.updateFundRateInfoByCode("510050");


    }
}
