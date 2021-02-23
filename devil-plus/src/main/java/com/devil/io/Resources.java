package com.devil.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Program: study
 * @Description: 将文件转换成stream流
 * @Author: Devil
 * @Create: 2021-02-23 11:23
 **/
public class Resources {

    private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    /**
     * 将文件路径转换成流
     *
     * @param resource 资源文件
     * @return {@link InputStream} 流
     */
    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(resource, null);
    }

    /**
     * 通过指定的类加载器加上默认的类加载器加载resource文件，将其转换成流
     * @param resource 资源文件
     * @param classLoader 类加载器
     * @return {@link InputStream}
     * @throws IOException
     */
    public static InputStream getResourceAsStream(String resource, ClassLoader classLoader) throws IOException {
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, classLoader);
        if (in == null) {
            throw new IOException("can not find resource " + resource);
        }
        return in;
    }
}
