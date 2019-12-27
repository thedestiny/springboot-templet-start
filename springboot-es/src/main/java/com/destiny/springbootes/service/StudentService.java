package com.destiny.springbootes.service;

import com.destiny.springbootes.entity.Student;
import com.destiny.springbootes.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void save(Student student) {
        studentRepository.save(student);
    }


}
