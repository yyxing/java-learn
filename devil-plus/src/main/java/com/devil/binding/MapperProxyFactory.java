package com.devil.binding;

import com.devil.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-26 17:12
 **/
public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    protected T newInstance(MapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

    /**
     * 创建mapper接口的代理实例
     *
     * @return 创建接口的代理实例
     */
    public T newInstance(SqlSession sqlSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession);
        return newInstance(mapperProxy);
    }

}
