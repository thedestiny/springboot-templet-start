package com.destiny.rabbit.utils;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

public class RabbitAppUtils {
	
	public static void main(String[] args) {
		
		
		System.out.println(IdWorker.getId());
		
		// "ni hao"
		String pinyin = PinyinUtil.getPinyin("你好", " ");
		System.out.println(pinyin);
	}
	
}
