package com.devil.study.context;

import com.devil.study.AppConfig;
import com.devil.study.AppTest;
import com.devil.study.stereotype.Component;
import com.devil.study.stereotype.ComponentScan;
import org.springframework.util.Assert;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @Program: spring
 * @Description:
 * @Author: Devil
 * @Create: 2021-03-11 18:05
 **/
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

	public AnnotationConfigApplicationContext() {

	}

	public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
		register(componentClasses);
		refresh();
	}

	public void register(Class<?>... componentClasses) {
		Assert.notEmpty(componentClasses, "At least one component class must be specified");
		try {
			for (Class<?> componentClass : componentClasses) {
				parseComponentClass(componentClass, false);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void parseComponentClass(Class<?> componentClass, boolean scanning) throws ClassNotFoundException {
		Annotation[] annotations = componentClass.getDeclaredAnnotations();
		// 判断类上标签是否加入bean中
		for (Annotation annotation : annotations) {
			// 直接加入到bean中
			if (annotation.getClass().isAssignableFrom(Component.class)) {
				Component component = (Component) annotation;
				String beanName = component.value();
				if (beanName.length() == 0) {
					beanName = componentClass.getSimpleName();
				}
				getBeanFactory().registerBean(beanName, componentClass);
			} else if (!scanning && annotation.getClass().isAssignableFrom(ComponentScan.class)) {
				ComponentScan scan = (ComponentScan) annotation;
				String basePackage = scan.value();
				scanBasePackage(basePackage);
			}
		}
	}

	private void scanBasePackage(String basePackage) throws ClassNotFoundException {
		// 扫描包下的所有类
		String basePath = ClassLoader.getSystemResource("").getPath() +
				basePackage.replace(".", "/");
		List<Class<?>> classes = new ArrayList<>();
		getClasses(basePath, basePackage, classes);
		for (Class<?> aClass : classes) {
			parseComponentClass(aClass, true);
		}
	}

	private void getClasses(String filePath, String packageName, List<Class<?>> classes) throws ClassNotFoundException {
		File base = new File(filePath);
		File[] files = base.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					String childPackageName = packageName + "." + file.getName();
					getClasses(file.getPath(), childPackageName, classes);
				} else {
					String filename = file.getName();
					if (filename.endsWith(".class")) {
						String className = packageName + "." + filename.substring(0, filename.length() - 6);
						Class<?> aClass = Class.forName(className);
						classes.add(aClass);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {
		AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
		configApplicationContext.scanBasePackage("com.devil");
	}
}
