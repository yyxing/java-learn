package com.devil.study.beans.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Program: spring
 * @Description:
 * @Author: Devil
 * @Create: 2021-03-11 18:41
 **/
public class DefaultBeanFactory implements BeanFactory {

	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
	private final Map<String, Object> earlyObjects = new ConcurrentHashMap<>();

	@Override
	public Object getBean(String name) {
		return null;
	}

	@Override
	public Object getBean(String name, Class<?> requireType) {
		return null;
	}

	@Override
	public Object getBean(Class<?> requireType) {
		return null;
	}

	@Override
	public void registerBean(String beanName, Class<?> beanClass) {
		// bean已经存在 不进行注册
		if (singletonObjects.containsKey(beanName)) {
			return;
		}
		// 解析类以及其中的属性 判断是否加入了@Autowired注解
		try {
			Object instance = createInstance(beanName,beanClass);
		} catch (Exception e) {

		}
	}

	private Object resolveDependency(String beanName, Class<?> beanClass) {

		return null;
	}

	private Object createInstance(String beanName, Class<?> beanClass) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<?> constructor = beanClass.getConstructor();
		return constructor.newInstance();
	}
}
