package com.destiny.mongodemo.service;

import com.destiny.mongodemo.entity.Elephant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElephantService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public void save(Elephant elephant) {
        mongoTemplate.save(elephant);
    }

    public List<Elephant> findByName(String name) {

        Query query = new Query(Criteria.where("name").is(name));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "age")));
        return mongoTemplate.find(query, Elephant.class);

    }


}
