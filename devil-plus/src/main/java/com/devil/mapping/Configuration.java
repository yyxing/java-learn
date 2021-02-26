package com.devil.mapping;

import com.devil.binding.MapperRegistry;
import com.devil.session.SqlSession;
import com.devil.session.defaults.DefaultSqlSession;
import com.mysql.cj.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Program: study
 * @Description: 全局配置类
 * @Author: Devil
 * @Create: 2021-02-23 12:02
 **/
public class Configuration {

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * mapper注册类
     */
    final MapperRegistry mapperRegistry = new MapperRegistry(this);
    /**
     * mapper映射集合 key：statementId value：MappedStatement
     */
    Map<String, MappedStatement> mappedStatements = new HashMap<>();

    /**
     * properties集合
     */
    Properties variables = new Properties();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public void setMappedStatements(Map<String, MappedStatement> mappedStatements) {
        this.mappedStatements = mappedStatements;
    }

    public Properties getVariables() {
        return variables;
    }

    public void setVariables(Properties variables) {
        this.variables = variables;
    }

    /**
     * 添加mapper映射到全局配置中
     *
     * @param key             mapper key值 namespace + id
     * @param mappedStatement mapper文件转换成的实体类
     */
    public void addMapperStatement(String key, MappedStatement mappedStatement) {
        if (mappedStatement == null || StringUtils.isNullOrEmpty(key)) {
            throw new RuntimeException("key or mappedStatement must be not null");
        }
        this.mappedStatements.put(key, mappedStatement);
    }

    /**
     * 添加mapper接口与对应代理类的映射
     *
     * @param type mapper接口类型
     * @param <T>
     */
    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    /**
     * 获取mapper接口与对应代理类的映射
     *
     * @param <T>
     * @param type       mapper接口类型
     * @param sqlSession
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public MappedStatement getMapperStatement(String id) {
        return this.mappedStatements.get(id);
    }
}
