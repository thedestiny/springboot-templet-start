package com.destiny.origin.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.destiny.origin.anno.LogRecord;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 日志记录
 * @Author liangwenchao
 * @Date 2022-03-30 2:06 PM
 */

@Slf4j
@Aspect
@Configuration
public class AppLogConfig {

    /**
     * 拦截切面 定时任务拦截
     */
    @Pointcut("execution(* com.destiny.origin.web..*(..))")
    public void pointCut() {
        log.info(" init pointCut");
    }

    @Pointcut("@annotation(com.destiny.origin.anno.LogRecord)")
    private void pointCut1() {
        log.info("切点");
    }


    @Around("pointCut1()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        //获取方法参数值数组
        Object[] params = point.getArgs();
        String jobName = point.getTarget().getClass().getName();
        //获取方法参数类型数组

        TimeInterval timer = DateUtil.timer();
        Object result = point.proceed(params);
        log.trace("jobName :: task {} cost {} ms execute params {}", jobName, timer.interval(), params);
        return result;
    }



}