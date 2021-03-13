package com.destiny.dog.learn;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;

import java.util.concurrent.TimeUnit;

public class HttpUtils {
	
	public static void main(String[] args) throws InterruptedException {
		
		TimeInterval timer = DateUtil.timer();
		System.out.println("start ");
		//---------------------------------
		//-------这是执行过程
		//---------------------------------
		TimeUnit.SECONDS.sleep(2);
		System.out.println("end ");
		
		System.out.println(timer.interval());//花费毫秒数
		System.out.println(timer.intervalSecond());//花费毫秒数
		
		
	}
}
