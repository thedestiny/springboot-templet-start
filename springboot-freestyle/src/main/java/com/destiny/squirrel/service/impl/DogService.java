package com.destiny.squirrel.service.impl;

import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author destiny
 * @Date 2021-06-04 11:24 AM
 */
@Service
public class DogService extends AbstractAnimalKlass {
    @Override
    public void eat() {
        System.out.println("it is dog eat ");
    }
}
