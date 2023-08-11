package com.water.controller;

import com.water.constans.BaseConstans;
import com.water.dto.LoginDTO;
import com.water.result.Result;
import com.water.service.UserService;
import com.water.vo.LoginVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/user")
public class UserContorller {
    @Resource
    UserService userService;
    @PostMapping("login")
    public Result login(@RequestBody LoginDTO loginDTO){
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        LoginVO res = userService.login(username, password);
        return Result.success(res);
    }
    @PostMapping("register")
    public Result register(@RequestBody LoginDTO loginDTO){
        String name = loginDTO.getName();
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        LoginVO res = userService.register(name,username,password);
        return Result.success(res);
    }

    @GetMapping("get")
    public Result getUser(){
        return Result.success(BaseConstans.SECCESS);
    }
}
