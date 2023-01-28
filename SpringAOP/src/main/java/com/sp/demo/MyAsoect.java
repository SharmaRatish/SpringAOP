package com.sp.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
@Aspect
@Component
public class MyAsoect {
	
	public static final Logger logger=LoggerFactory.getLogger(MyAsoect.class);
	
	@Around("@annotation(com.sp.demo.annotation.LogTimeExecution)")
	public Object getExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable
	{
		
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		Object p=joinPoint.proceed();
		stopwatch.stop();
		logger.info("{} executed in time {}",joinPoint.getSignature(),
				stopwatch.getTotalTimeMillis());
		
		return p;
		
	}

}
