package com.devil.executor;

import com.devil.io.Resources;
import com.devil.mapping.BoundSql;
import com.devil.mapping.Configuration;
import com.devil.mapping.MappedStatement;
import com.devil.mapping.ParameterMapping;
import com.devil.parsing.GenericTokenParser;
import com.devil.parsing.ParameterMappingTokenHandler;
import com.devil.utils.StrUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-24 17:46
 **/
public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(MappedStatement ms, Object... params) throws Exception {
        // 注册驱动获取连接
        Connection conn = getConnection(ms.getConfiguration());
        // 获取解析后sql
        BoundSql boundSql = buildBoundSql(ms.getSql());
        // 获取preparedStatement
        PreparedStatement statement = buildPreparedStatement(conn, boundSql);
        // 设置参数
        setParameterToSql(statement, boundSql, ms.getParameterType(), params);
        // 执行sql
        ResultSet resultSet = statement.executeQuery();
        // 拼接返回集合
        return buildResultSet(resultSet, ms.getResultType());
    }

    private <E> List<E> buildResultSet(ResultSet resultSet, String resultType) throws Exception {
        if (StrUtil.isEmpty(resultType)) {
            throw new NullPointerException("resultType must not be null");
        }
        // 获取返回类
        Class<?> resultClass = Resources.classForName(resultType);
        // 获取返回类的无参构造函数
        Constructor<?> constructor = resultClass.getConstructor();
        // 获得结果
        List<E> resultList = new ArrayList<>();
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 执行构造函数生成新的返回类实例
            Object instance = constructor.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 获取列名称
                String columnName = metaData.getColumnName(i);
                // 将列名称转换成字段名称
                String fieldName = StrUtil.toCamelCase(columnName);
                // 获取列的值
                Object columnValue = resultSet.getObject(columnName);
                // Java内省方法可以使用类字段的读写方法去写入读取数据
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, resultClass);
                // 获取写方法
                Method writeMethod = propertyDescriptor.getWriteMethod();
                // 执行写方法到指定实例对象中
                writeMethod.invoke(instance, columnValue);
            }
            // 加入到结果集合中
            resultList.add((E) instance);
        }
        return resultList;
    }

    private void setParameterToSql(PreparedStatement statement, BoundSql boundSql,
                                   String parameterType, Object... params) throws Exception {
        if (StrUtil.isEmpty(parameterType)) {
            throw new NullPointerException("parameterType must not be null");
        }
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Class<?> parameterClass = Resources.classForName(parameterType);
        Object parameterValue = null;
        if (params != null){
            for (Object p : params) {
                if (p.getClass() == parameterClass) {
                    parameterValue = p;
                }
            }
        }
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String parameterName = parameterMapping.getContent();
            // 通过反射获取Field
            Field parameterField = parameterClass.getDeclaredField(parameterName);
            // 暴力访问获取private值
            parameterField.setAccessible(true);
            // 获取Field的值
            Object fieldValue = parameterField.get(parameterValue);
            // 将值设置
            statement.setObject(i + 1, fieldValue);
        }
    }

    @Override
    public int update(MappedStatement ms, Object... params) throws Exception {
        return 0;
    }

    private BoundSql buildBoundSql(String sql) {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
        String newSql = parser.parse(sql);
        return new BoundSql(newSql, tokenHandler.getParameterMappings());
    }

    private Connection getConnection(Configuration configuration) throws SQLException {
        return configuration.getDataSource().getConnection();
    }

    private PreparedStatement buildPreparedStatement(Connection conn, BoundSql boundSql) throws SQLException {
        return conn.prepareStatement(boundSql.getSql());
    }
}
