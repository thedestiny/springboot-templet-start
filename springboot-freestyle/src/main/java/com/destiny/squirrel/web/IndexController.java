package com.destiny.squirrel.web;

import com.destiny.squirrel.entity.User;
import com.destiny.squirrel.service.AnimalKlass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class IndexController {

    /**
     * 测试注入依赖 animalKlassMap，根据service 名称和实现类进行导入
     * catService -> CatService
     * dogService -> DogService
     * */
    @Autowired
    private Map<String, AnimalKlass> animalKlassMap;


    @PostMapping(value = "/index")
    public String index(@RequestBody User user) {

        log.info("user is {}", user);

        animalKlassMap.forEach((key, value) -> {
            System.out.println(key + " and " + value);
        });
        return "333";
    }


    @GetMapping(value = "/")
    public String home(User user) {

        log.info("user is {}", user);

        animalKlassMap.forEach((key, value) -> {
            value.eat();
            System.out.println(key + " and " + value);
        });





        return "333";
    }

}
