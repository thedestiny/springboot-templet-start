package com.destiny.wolf.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class WolfAppEvent extends ApplicationEvent {
	
	private String name;
	
	
	/**
	 * Create a new {@code ApplicationEvent}.
	 *
	 * @param source the object on which the event initially occurred or with
	 *               which the event is associated (never {@code null})
	 */
	public WolfAppEvent(Object source) {
		super(source);
	}
	
	public WolfAppEvent(Object source, String name) {
		super(source);
		this.name = name;
	}
}
