package com.devil.executor;

import com.devil.mapping.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-24 17:41
 **/
public interface Executor {

    /**
     * 具体执行查询的方法 这里与jdbc交互
     *
     * @param ms     sql信息实体
     * @param params 参数数组
     * @param <E>
     * @return 返回结果
     */
    <E> List<E> query(MappedStatement ms, Object... params) throws Exception;

    /**
     * 执行修改逻辑
     *
     * @param ms     sql信息实体
     * @param params 参数数组
     * @return 成功条数
     */
    int update(MappedStatement ms, Object... params) throws Exception;
}
