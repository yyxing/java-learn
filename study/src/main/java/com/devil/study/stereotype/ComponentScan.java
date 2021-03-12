package com.devil.study.stereotype;

import java.lang.annotation.*;

/**
 * @Program: spring
 * @Description:
 * @Author: Devil
 * @Create: 2021-03-12 10:15
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {

	String value() default "";
}
