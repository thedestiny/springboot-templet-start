package com.destiny.leopard.service.impl;

import com.destiny.leopard.service.DestinyService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author destiny
 * @Date 2021-05-10 10:45 AM
 */
@Service
public class LeopardDestinyServiceImpl implements DestinyService {


    public String sayHello(String msg) {
        return "->" + msg;
    }



}
