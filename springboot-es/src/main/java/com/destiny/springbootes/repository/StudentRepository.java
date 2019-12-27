package com.destiny.springbootes.repository;

import com.destiny.springbootes.entity.Student;
import com.destiny.springbootes.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StudentRepository extends ElasticsearchRepository<Student, Long> {
}
