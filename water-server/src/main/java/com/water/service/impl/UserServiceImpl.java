package com.water.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water.constans.ExceptionConstants;
import com.water.constans.UserConstants;
import com.water.entity.TokenBind;
import com.water.entity.User;
import com.water.exception.LoginFailException;
import com.water.mapper.UserMapper;
import com.water.redis.TokenRedis;
import com.water.service.UserService;
import com.water.vo.LoginVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public LoginVO login(String username, String password) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.lambda().eq(User::getUsername,username).eq(User::getPassword,password);
        User user = userMapper.selectOne(qw);
        if(user == null){
            throw new LoginFailException(ExceptionConstants.USER_LOGIN_FAIL);
        }
        LoginVO loginVO = new LoginVO();
        loginVO.setId(user.getId());
        loginVO.setName(user.getName());
        loginVO.setToken(getToken(user.getId(),username));
        loginVO.setAvatar(UserConstants.DEFUALT_AVATAR);
        return loginVO;
    }

    @Override
    public void register(String name, String username, String password) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.lambda().eq(User::getUsername,username);
        if (userMapper.selectOne(qw) != null) {
            throw new LoginFailException(ExceptionConstants.USER_REGISTER_FAIL);
        }
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateDate(new Date());
        user.setAvatar(UserConstants.DEFUALT_AVATAR);
        userMapper.insert(user);
    }

    @Override
    public User getUser() {
        return null;
    }

    public String getToken(Long id,String username){
        String data = (id + "-" + username + "-" + new Date());

        MD5 md5 = new MD5();
        String token = md5.digestHex16(data);

        TokenBind tokenBind = new TokenBind();
        tokenBind.setId(id);
        tokenBind.setUsername(username);
        tokenBind.setToken(token);

        TokenRedis.setTokenBind(token,tokenBind);
        return null;
    }
}
