package com.devil.study.context;

import com.devil.study.beans.factory.BeanFactory;
import com.devil.study.beans.factory.DefaultBeanFactory;

/**
 * @Program: spring
 * @Description:
 * @Author: Devil
 * @Create: 2021-03-11 17:23
 **/
public abstract class AbstractApplicationContext {

	private final BeanFactory beanFactory = new DefaultBeanFactory();

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void refresh() {

	}
}
