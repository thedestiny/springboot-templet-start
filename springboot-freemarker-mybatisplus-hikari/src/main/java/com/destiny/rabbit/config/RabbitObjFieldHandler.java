package com.destiny.rabbit.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 *
 * 公共字段填充
 * */
// https://blog.csdn.net/weixin_37703281/article/details/102751544

@Slf4j
public class RabbitObjFieldHandler implements MetaObjectHandler {
	
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";
	
	@Override
	public void insertFill(MetaObject metaObject) {
		handle(CREATETIME, metaObject, new Date());
		handle(UPDATETIME, metaObject, new Date());
	}
	
	@Override
	public void updateFill(MetaObject metaObject) {
		handle(UPDATETIME, metaObject, new Date());
	}
	
	
	private void handle(String name, MetaObject metaObject, Object target) {
		Object o = getFieldValByName(name, metaObject);
		if (o == null) {
			setFieldValByName(name, target, metaObject);
		}
	}
}
