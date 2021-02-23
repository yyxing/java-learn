package com.devil.session;

import com.devil.builder.xml.XMLConfigBuilder;
import com.devil.mapping.Configuration;
import com.devil.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-23 14:03
 **/
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) {
        try {
            // 使用parser将xml解析成容器对象
            XMLConfigBuilder parser = new XMLConfigBuilder(in);
            return build(parser.parse());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
