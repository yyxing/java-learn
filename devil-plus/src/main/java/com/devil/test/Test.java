package com.devil.test;

import com.devil.io.Resources;
import com.devil.session.SqlSession;
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
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(3);
        user.setUsername("lulu3");
        user.setPhoneNumber("17835416638");
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(userMapper.delete(user));
    }
}
