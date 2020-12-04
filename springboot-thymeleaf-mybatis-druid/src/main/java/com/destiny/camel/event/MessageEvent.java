package com.destiny.camel.event;

import org.springframework.context.ApplicationEvent;


public class MessageEvent extends ApplicationEvent {
	
	
	public MessageEvent(Object source) {
		super(source);
	}
	
	private String msg;
	
	public MessageEvent(Object source, String msg) {
		super(source);
		this.msg = msg;
	}
	
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
