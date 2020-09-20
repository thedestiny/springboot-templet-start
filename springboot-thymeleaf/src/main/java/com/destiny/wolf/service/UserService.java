package com.destiny.wolf.service;


import com.destiny.wolf.entity.User;
import com.destiny.wolf.repository.UserRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void add(User user) {
        userRepository.save(user);
    }


    public void queryUserList() {

        QueryBuilder qb1 = QueryBuilders.termsQuery("name", "x");
        Iterable<User> search = userRepository.search(qb1);
        Iterator<User> iterator = search.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        for (Iterator it = search.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }

    }


}
