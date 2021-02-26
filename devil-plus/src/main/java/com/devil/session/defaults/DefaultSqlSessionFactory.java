package com.devil.session.defaults;

import com.devil.executor.SimpleExecutor;
import com.devil.mapping.Configuration;
import com.devil.session.SqlSession;
import com.devil.session.SqlSessionFactory;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-23 14:43
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    /**
     * 数据源全局配置
     */
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration config) {
        this.configuration = config;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration, new SimpleExecutor());
    }
}
