package com.devil.mapping;

/**
 * @Program: study
 * @Description: 将mapper文件的内容转换成实体类
 * @Author: Devil
 * @Create: 2021-02-23 11:57
 **/
public class MappedStatement {

    /**
     * 数据源全局配置
     */
    private Configuration configuration;

    /**
     * 同一mapper中方法唯一id
     */
    private String id;

    /**
     * 一次最多获取多少行数据
     */
    private Integer fetchSize;

    /**
     * 请求数据源获取数据的最多请求时间
     */
    private Integer timeout;

    /**
     * 参数类型
     */
    private String parameterType;

    /**
     * 返回类型
     */
    private String resultType;

    /**
     * sql语句
     */
    private String sql;

    /**
     * sql类型 select update insert
     */
    private SqlCommandType sqlCommandType;

    public MappedStatement(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(String sqlCommandName) {
        switch (sqlCommandName){
            case "select":
                this.sqlCommandType = SqlCommandType.SELECT;
                break;
            case "update":
                this.sqlCommandType = SqlCommandType.UPDATE;
                break;
            case "delete":
                this.sqlCommandType = SqlCommandType.DELETE;
                break;
            case "insert":
                this.sqlCommandType = SqlCommandType.INSERT;
                break;
            default:
                this.sqlCommandType = SqlCommandType.UNKNOWN;
                break;
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
