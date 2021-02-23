package com.devil;

import com.devil.io.Resources;
import com.devil.session.SqlSessionFactory;
import com.devil.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-23 16:02
 **/
public class SqlSessionFactoryTest {

    @Test
    public void test() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }
}
