package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Component//交给spring容器管理
//@Aspect//标识为切面
public class RedisUpdateAspect {
		
	
	//@Around("@annotation(cache_find)")
	public Object around(ProceedingJoinPoint joinPoint) {
		return null;
		
	}
}
