package com.devil.study.beans.factory;

/**
 * @Program: spring
 * @Description:
 * @Author: Devil
 * @Create: 2021-03-11 18:00
 **/
public interface BeanFactory {

	/**
	 * 根据名称获取bean
	 *
	 * @param name bean名称
	 * @return
	 */
	Object getBean(String name);

	/**
	 * 根据名称和class类型获取bean
	 *
	 * @param name        bean名称
	 * @param requireType bean的class类型
	 * @return
	 */
	Object getBean(String name, Class<?> requireType);

	/**
	 * 根据bean的class类型获取bean
	 *
	 * @param requireType bean的class类型
	 * @return
	 */
	Object getBean(Class<?> requireType);


	/**
	 * 注册bean信息
	 * @param beanName
	 * @param beanClass
	 */
	void registerBean(String beanName, Class<?> beanClass);
}
