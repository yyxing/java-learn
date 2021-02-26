package com.devil.session;

import com.devil.mapping.Configuration;

import java.util.List;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-24 16:59
 **/
public interface SqlSession {

    /**
     * 查询数据list
     *
     * @param statement 指向mapper的key
     * @param <T>
     * @param params 参数数组
     * @return
     */
    <T> List<T> selectList(String statement, Object... params);

    /**
     * 查询单条数据
     *
     * @param statement 指向mapper的key
     * @param <T>
     * @param params 参数数组
     * @return
     */
    <T> T selectOne(String statement, Object... params);

    /**
     * 插入数据
     *
     * @param statement 指向mapper的key
     * @param params 参数数组
     * @return 插入成功数量
     */
    int insert(String statement, Object... params);

    /**
     * 更新数据
     *
     * @param statement 指向mapper的key
     * @param params 参数数组
     * @return 更新成功数量
     */
    int update(String statement, Object... params);

    /**
     * 删除数据
     *
     * @param statement 指向mapper的key
     * @param params 参数数组
     * @return 删除成功数量
     */
    int delete(String statement, Object... params);

    /**
     * 事务提交
     */
    void commit();

    /**
     * 事务回滚
     */
    void rollback();

    /**
     * 获取接口的Mapper对象
     * @param type 接口的class类型
     * @return 接口的代理类
     */
    <T> T getMapper(Class<T> type);

    /**
     * Retrieves current configuration.
     * @return Configuration
     */
    Configuration getConfiguration();
}
