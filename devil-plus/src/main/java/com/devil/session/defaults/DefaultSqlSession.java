package com.devil.session.defaults;

import com.devil.executor.Executor;
import com.devil.executor.SimpleExecutor;
import com.devil.mapping.Configuration;
import com.devil.mapping.MappedStatement;
import com.devil.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-24 17:07
 **/
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;
    private final Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> List<T> selectList(String statement, Object... params) {
        try {
            MappedStatement ms = configuration.getMapperStatement(statement);
            return executor.query(ms, params);
        } catch (Exception e) {
            throw new RuntimeException("Error querying database.  Cause: " + e, e);
        }
    }

    @Override
    public <T> T selectOne(String statement, Object... params) {
        List<T> list = this.selectList(statement, params);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public int insert(String statement, Object... params) {
        return 0;
    }

    @Override
    public int update(String statement, Object... params) {
        return 0;
    }

    @Override
    public int delete(String statement, Object... params) {
        return 0;
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
