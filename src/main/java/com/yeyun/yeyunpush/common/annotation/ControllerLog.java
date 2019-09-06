package com.yeyun.yeyunpush.common.annotation;

import java.lang.annotation.*;

/**
 * controller层日志拦截注解
 * @author CZH
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented
public @interface ControllerLog {

	String value() default "";
	int type() default 0;
	
}
