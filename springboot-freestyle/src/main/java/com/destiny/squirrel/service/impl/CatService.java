package com.destiny.squirrel.service.impl;

import com.destiny.squirrel.service.AnimalKlass;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-06-04 11:24 AM
 */
@Service
public class CatService extends AbstractAnimalKlass implements AnimalKlass {
    @Override
    public void eat() {
        System.out.println("it is cat eat");
    }
}
