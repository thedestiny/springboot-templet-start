package com.destiny.squirrel.web;

import com.destiny.squirrel.config.ResponseResult;
import com.destiny.squirrel.entity.User;
import com.destiny.squirrel.service.AnimalKlass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            log.info(key + " and " + value);
        });
        return "333";
    }


    @GetMapping(value = "/")
    @ResponseResult
    public Object home(User user) {

        log.info("user is {}", user);

        animalKlassMap.forEach((key, value) -> {
            value.eat();
            log.info(key + " and " + value);
        });

        return "333";
    }

    /**
     * http://xxxx:8000/test/xxx.yyy.zzz
     *
     * */
    @GetMapping(value = "/api/test/{xxx:.+}")
    public String temp(@PathVariable(value = "xxx") String xxx){
        log.info(xxx);
        return "ss";
    }




}
