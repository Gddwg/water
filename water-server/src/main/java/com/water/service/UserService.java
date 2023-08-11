package com.water.service;

import com.water.entity.User;
import com.water.vo.LoginVO;

public interface UserService {
    LoginVO login(String username, String password);
    LoginVO register(String name, String username, String password);
    User getUser();
}
