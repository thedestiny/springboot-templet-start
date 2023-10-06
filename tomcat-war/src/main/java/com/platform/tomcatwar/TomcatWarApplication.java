package com.platform.tomcatwar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TomcatWarApplication {


    public static void main(String[] args) {
        System.out.println("start app ! ");
        SpringApplication.run(TomcatWarApplication.class, args);
    }

}
