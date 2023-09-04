package com.water.controller;

import com.water.constans.BaseConstants;
import com.water.dto.LoginDTO;
import com.water.result.Result;
import com.water.service.UserService;
import com.water.vo.LoginVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Resource
    UserService userService;
    @PostMapping("login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO){
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        if(username == null || password == null){
            return Result.error(BaseConstants.ERROR_MESSAGE);
        }
        LoginVO res = userService.login(username, password);
        return Result.success(res);
    }
    @PostMapping("register")
    public Result<String> register(@RequestBody LoginDTO loginDTO){
        String name = loginDTO.getName();
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        if(name == null || username == null || password == null) {
            return Result.error(BaseConstants.ERROR_MESSAGE);
        }
        userService.register(name,username,password);
        return Result.success(BaseConstants.SUCCESS);
    }

    @GetMapping("get")
    public Result<String> getUser(){
        return Result.success(BaseConstants.SUCCESS);
    }
}
