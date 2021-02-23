package com.devil.test;

import com.devil.io.Resources;
import com.devil.session.SqlSessionFactory;
import com.devil.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-23 16:10
 **/
public class Test {

    public static void main(String[] args) throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }
}
