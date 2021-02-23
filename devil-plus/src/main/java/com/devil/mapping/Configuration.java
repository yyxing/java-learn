package com.devil.mapping;

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
}
