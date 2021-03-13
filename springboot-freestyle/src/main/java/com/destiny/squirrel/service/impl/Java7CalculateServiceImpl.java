package com.destiny.squirrel.service.impl;

import com.destiny.squirrel.service.CalculateService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class Java7CalculateServiceImpl  implements CalculateService {
	@Override
	public Integer sum(Integer... values) {
		System.out.println("Java 7 for 循环实现 ");
		int sum = 0;
		for (int i = 0; i < values.length; i++) {
			sum += values[i];
		}
		return sum;
	}
}
