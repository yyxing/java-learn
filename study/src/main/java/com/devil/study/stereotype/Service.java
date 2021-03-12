package com.devil.study.stereotype;

import java.lang.annotation.*;

/**
 * @Program: spring
 * @Description:
 * @Author: Devil
 * @Create: 2021-03-11 17:51
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

	String value() default "";
}
