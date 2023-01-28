package com.sp.demo;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class SpringAspect {
	
	public static final Logger logger=LoggerFactory.getLogger(SpringAspect.class);
	
	@Autowired
	private ObjectMapper mapper;
	
	@Pointcut("within(com.sp.demo.controller..*)"+"&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void pointcut()
	{
		
		
	}
	private Map<String, String> getParameter (JoinPoint joinPoint)
	{
		CodeSignature signature=(CodeSignature)joinPoint.getSignature();
		Map<String, String> map=new HashMap<String,String>();
		String [] name=signature.getParameterNames();
		for (int i = 0; i < name.length; i++) {
			map.put(name[i], String.valueOf(joinPoint.getArgs()[i]));
		}
		return map;
	}
	@Before("pointcut()")
	public void logMethod(JoinPoint joinPoint)
	{
		MethodSignature signature=(MethodSignature)joinPoint.getSignature();
		RequestMapping mapping=signature.getMethod().getAnnotation(RequestMapping.class);
		Map<String, String> map=getParameter(joinPoint);
		try {
			logger.info("==>path(s) : {},method(s) : {},argument : {}", mapping.path(), mapping.method(), mapper.writeValueAsString(map));
			
			
		} catch (JsonProcessingException e) {
			// TODO: handle exception
		}
		
	}
	@AfterReturning(pointcut = "pointcut()",returning = "reEntity")
	public void logMethodAfter(JoinPoint joinPoint,ResponseEntity<?> reEntity)
	{
		MethodSignature signature=(MethodSignature)joinPoint.getSignature();
		RequestMapping mapping=signature.getMethod().getAnnotation(RequestMapping.class);
		try {
			logger.info("==>path(s) : {},method() : {},returning : {}",
					mapping.path(), mapping.method(), mapper.writeValueAsString(reEntity));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	

}
