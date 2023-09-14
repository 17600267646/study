package com.person.study.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
@Slf4j
public class LogAspect {

//    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.person.study.*.*(..))") // 定义切点为com.example包下的所有方法
    public void logPointcut() {}

    @Before("logPointcut()") // 在切点方法执行前执行
    public void doBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodPath = className + "." + methodName;
        log.info("Method Path: " + methodPath);
        log.info("Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "result") // 在切点方法执行后执行
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodPath = className + "." + methodName;
        log.info("Method Path: " + methodPath);
        log.info("Return: " + result);
    }
}
