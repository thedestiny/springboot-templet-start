package com.destiny.rabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class RabbitApplicationRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(" running {}", args);

        HashMap<String,String> dd = new HashMap<>();

        dd.put("dd","rr");

        ConcurrentHashMap<String,String> ddd = new ConcurrentHashMap<>();
        ddd.put("e","4");

    }


}
