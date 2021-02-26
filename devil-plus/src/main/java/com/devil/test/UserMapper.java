package com.devil.test;

import java.util.List;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-26 18:35
 **/
public interface UserMapper {

    User findById(User user);

    List<User> selectList();
}
