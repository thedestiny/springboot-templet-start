package com.destiny.hedghog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Description springboot application
 * @Author liangwenchao
 * @Date 2021-11-26 4:45 PM
 */

@Slf4j
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HedghogApplication {


    public static void main(String[] args) {

        log.info("start app HedghogApplication --> ");
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(HedghogApplication.class)
                .run(args);

    }


}
