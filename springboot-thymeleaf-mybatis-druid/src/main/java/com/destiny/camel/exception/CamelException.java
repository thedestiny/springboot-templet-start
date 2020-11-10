package com.destiny.camel.exception;

import lombok.Data;

@Data
public class CamelException extends RuntimeException {
	
	public CamelException() {
	}
	
	public CamelException(String message) {
		super(message);
	}
}
