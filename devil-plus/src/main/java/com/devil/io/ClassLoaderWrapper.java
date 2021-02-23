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

    ClassLoader[] getClassLoader(ClassLoader classLoader) {
        return new ClassLoader[]{
                systemClassLoader,
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader(),
                classLoader
        };
    }
}
