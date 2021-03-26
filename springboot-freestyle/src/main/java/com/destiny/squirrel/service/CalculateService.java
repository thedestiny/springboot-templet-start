package com.destiny.squirrel.service;

public interface CalculateService {
	// // https://mp.weixin.qq.com/s/45S-_XIWgkR6qYbFKll5RQ
	/**
	 * 从多个整数 sum 求和
	 * @param values 多个整数
	 * @return sum 累加值
	 */
	Integer sum(Integer... values);
	
}
