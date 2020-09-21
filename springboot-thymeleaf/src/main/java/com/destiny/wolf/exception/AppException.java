package com.destiny.wolf.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException {
	
	// 错误信息和错误码
	private String msg;
	
	private Integer code;
	
	public AppException(String msg) {
		super(msg);
		this.code = 500;
	}
	
	
}
