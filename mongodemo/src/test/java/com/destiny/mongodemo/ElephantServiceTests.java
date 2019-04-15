package com.destiny.mongodemo;


import com.destiny.mongodemo.entity.Elephant;
import com.destiny.mongodemo.service.ElephantService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElephantServiceTests extends MongodemoApplicationTests {


    @Autowired
    private ElephantService service;

    @Test
    public void test() {


        for (int i = 0; i < 100; i++) {

            Elephant e = new Elephant();
            e.setAddress("urs" + i);
            e.setId((long) i);
            e.setName("小李");
            e.setAge(i + 10);
            service.save(e);

        }

    }

    @Test
    public void test02() {

        List<Elephant> elephants = service.findByName("小李");

        elephants.forEach(System.out::println);

    }


}
