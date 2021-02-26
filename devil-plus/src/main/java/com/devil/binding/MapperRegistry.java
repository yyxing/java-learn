package com.devil.binding;

import com.devil.mapping.Configuration;
import com.devil.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: study
 * @Description: Mapper接口的注册方式
 * @Author: Devil
 * @Create: 2021-02-26 14:00
 **/
public class MapperRegistry {
    private final Configuration config;
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public MapperRegistry(Configuration config) {
        this.config = config;
    }

    /**
     * 将mapper接口添加到map集合中
     *
     * @param type mapper接口类
     */
    public <T> void addMapper(Class<T> type) {
        // 只代理Mapper接口
        if (type.isInterface()){
            if (hasMapper(type)) {
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    /**
     * 获取接口的代理实例
     *
     * @param type 接口类型
     * @return {@link T} 接口的代理实例
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        // 获取该类对应的Mapper代理工厂
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (null == mapperProxyFactory){
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            // 通过工厂创造一个实例
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }
}
