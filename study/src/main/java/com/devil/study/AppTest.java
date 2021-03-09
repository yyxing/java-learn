package com.devil.study;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Program: spring
 * @Description:
 * @Author: Devil
 * @Create: 2021-03-09 11:45
 **/
public class AppTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		AppService appService = context.getBean(AppService.class);
		System.out.println(appService);
	}
}
