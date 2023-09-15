package com.person.study.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

//    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.person.study.controller..*(..))") // 定义切点为com.example包及其子包下的所有方法
    public void logPointcut() {}

    @Before("logPointcut()") // 在切点方法执行前执行
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodPath = className + "/" + methodName;
        log.info("开始调用/" + methodPath+"请求参数"+ JSONUtil.toJsonStr(joinPoint.getArgs()));

    }

    @AfterReturning(pointcut = "logPointcut()", returning = "result") // 在切点方法执行后执行
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
//        String methodPath = className + "/" + methodName;
//        logger.error("Method Exit: " + methodPath);
        log.info("返回结果" + JSONUtil.toJsonStr(result));
    }
}
