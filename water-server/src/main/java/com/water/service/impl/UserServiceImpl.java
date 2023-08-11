package com.water.service.impl;

import com.water.entity.User;
import com.water.mapper.UserMapper;
import com.water.service.UserService;
import com.water.vo.LoginVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public LoginVO login(String username, String password) {
        // TODO 校验
        // TODO 生成token
        // TODO 存入redis
        //userMapper.selectList();
        return null;
    }

    @Override
    public LoginVO register(String name, String username, String password) {
        // TODO 注册
        return null;
    }

    @Override
    public User getUser() {
        return null;
    }
}
