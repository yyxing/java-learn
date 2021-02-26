package com.devil.io;

import java.io.InputStream;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-23 14:07
 **/
public class ClassLoaderWrapper {

    ClassLoader systemClassLoader;

    ClassLoaderWrapper() {
        try {
            systemClassLoader = ClassLoader.getSystemClassLoader();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public InputStream getResourceAsStream(String resource) {
        return getResourceAsStream(resource, getClassLoader(null));
    }

    public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
        return getResourceAsStream(resource, getClassLoader(classLoader));
    }

    /**
     * 将资源文件变成流
     *
     * @param resource     资源文件
     * @param classLoaders 类加载器数组
     * @return 文件流
     */
    InputStream getResourceAsStream(String resource, ClassLoader[] classLoaders) {
        for (ClassLoader cl : classLoaders) {
            if (null != cl) {
                // 尝试获取资源流
                InputStream returnValue = cl.getResourceAsStream(resource);
                // 若找不到资源流 尝试加上/重新加载
                if (null == returnValue) {
                    returnValue = cl.getResourceAsStream("/" + resource);
                }
                if (null != returnValue) {
                    return returnValue;
                }
            }
        }
        return null;
    }

    /**
     * 获取当前上下文所有的ClassLoader
     *
     * @param classLoader 要添加的ClassLoader
     * @return ClassLoader集合
     */
    ClassLoader[] getClassLoader(ClassLoader classLoader) {
        return new ClassLoader[]{
                systemClassLoader,
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader(),
                classLoader
        };
    }

    /**
     * 将className转换成对应的Class
     *
     * @param className class全路径
     * @return {@link Class} 转换后的类
     */
    public Class<?> classForName(String className) throws ClassNotFoundException {
        return classForName(className, getClassLoader(null));
    }

    /**
     * 将className转换成对应的Class
     *
     * @param className    class全路径
     * @param classLoaders 类加载器数组
     * @return {@link Class} 转换后的类
     */
    public Class<?> classForName(String className, ClassLoader[] classLoaders) throws ClassNotFoundException {
        for (ClassLoader cl : classLoaders) {
            if (null != cl) {
                try {
                    return Class.forName(className, true, cl);
                } catch (ClassNotFoundException e) {
                    // we'll ignore this until all classloaders fail to locate the class
                }
            }
        }
        throw new ClassNotFoundException("Cannot find class: " + className);
    }
}
