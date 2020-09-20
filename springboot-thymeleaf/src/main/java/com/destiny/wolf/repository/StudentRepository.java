package com.destiny.wolf.repository;

import com.destiny.wolf.entity.Student;
import com.destiny.wolf.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StudentRepository extends ElasticsearchRepository<Student, Long> {
}
