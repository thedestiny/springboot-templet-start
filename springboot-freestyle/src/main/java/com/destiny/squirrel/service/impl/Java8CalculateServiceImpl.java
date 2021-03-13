package com.destiny.squirrel.service.impl;

import com.destiny.squirrel.service.CalculateService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@Profile("dev")
public class Java8CalculateServiceImpl implements CalculateService {
	@Override
	public Integer sum(Integer... values) {
		System.out.println("Java 8 Lambda 实现");
		int sum = Stream.of(values).reduce(0, Integer::sum);
		return sum;
	}
}
