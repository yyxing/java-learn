package com.devil.study.stereotype;

import java.lang.annotation.*;

/**
 * @Program: spring
 * @Description:
 * @Author: Devil
 * @Create: 2021-03-11 17:50
 **/
// 标识在类上
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

	/**
	 * beanName
	 * @return
	 */
	String value() default "";
}
