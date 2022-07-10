package com.nowcoder.community.service;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//考虑到前一个查出来的userId是外键，但是我们显示的时候是需要看到姓名的，那么就再设置一个service
@Service
public class UserService {

    @Autowired //查询的是用户，需要将用户的mapper注入进来
    private UserMapper userMapper;

    //查询一个用户
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

}
