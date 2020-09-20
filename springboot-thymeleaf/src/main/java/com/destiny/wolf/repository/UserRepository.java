package com.destiny.wolf.repository;

import com.destiny.wolf.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User, Long> {
}
