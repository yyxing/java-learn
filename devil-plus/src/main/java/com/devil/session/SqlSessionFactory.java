package com.devil.session;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-23 14:22
 **/
public interface SqlSessionFactory {

    /**
     * 生成SqlSession SqlSession用于封装JDBC操作 与数据库真正的连接擦欧总
     * @return {@link SqlSession}
     */
    SqlSession openSession();
}
