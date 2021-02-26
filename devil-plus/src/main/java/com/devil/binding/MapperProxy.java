package com.devil.binding;

import com.devil.mapping.Configuration;
import com.devil.mapping.MappedStatement;
import com.devil.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-26 17:32
 **/
public class MapperProxy<T> implements InvocationHandler, Serializable {
    private final SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invokeMapperMethod(method, args, sqlSession);
    }


    private Object invokeMapperMethod(Method method, Object[] args, SqlSession sqlSession) {
        Class<?> mapperInterface = method.getDeclaringClass();
        String methodName = method.getName();
        String statementId = mapperInterface.getName() + "." + methodName;
        Configuration config = sqlSession.getConfiguration();
        MappedStatement mapperStatement = config.getMapperStatement(statementId);
        Object result = null;
        switch (mapperStatement.getSqlCommandType()) {
            case DELETE: {
                result = sqlSession.delete(statementId, args);
                break;
            }
            case INSERT:
                result = sqlSession.insert(statementId, args);
                break;
            case SELECT:
                Class<?> returnType = method.getReturnType();
                if (returnType.equals(void.class)) {
                    result = null;
                    break;
                } else if (Collection.class.isAssignableFrom(returnType) || returnType.isArray()) {
                    result = sqlSession.selectList(statementId, args);
                } else {
                    result = sqlSession.selectOne(statementId, args);
                }
                break;
            case UPDATE:
                result = sqlSession.update(statementId, args);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + mapperStatement.getSqlCommandType());
        }
        return result;
    }
}
