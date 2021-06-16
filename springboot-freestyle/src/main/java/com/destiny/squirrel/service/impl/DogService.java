package com.destiny.squirrel.service.impl;

import com.destiny.squirrel.service.AnimalKlass;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-06-04 11:24 AM
 */
@Service
public class DogService extends AbstractAnimalKlass {
    @Override
    public void eat() {
        System.out.println("it is dog eat ");
    }
}
