package com.destiny.origin.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * 很多时候当我们完成某些业务后需要给用户推送相关消息提醒。
 * 对于这种非核心业务功能我们可以拿出来，创建一个事件去异步执行，从而实现核心业务和子业务的解耦。
 */
@Slf4j
public class AppOriginEvent extends ApplicationEvent {

    private String message;
    /**
     * Create a ApplicationEvent
     */
    public AppOriginEvent(Object source,String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
