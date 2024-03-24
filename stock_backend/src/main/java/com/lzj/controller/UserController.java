package com.lzj.controller;

import com.lzj.pojo.entity.SysUser;
import com.lzj.service.UserService;
import com.lzj.vo.req.LoginReqVo;
import com.lzj.vo.resp.LoginRespVo;
import com.lzj.vo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserService userService;



    @RequestMapping("user/{userName}")
    public SysUser getUserByName(@PathVariable("UserName") String userName){
            return userService.getUserByName(userName);
    }

    @PostMapping("/login")
    public Result<LoginRespVo> login(@RequestBody LoginReqVo loginReqVo) {
        Result<LoginRespVo> loginRespVo=userService.login(loginReqVo);
        return loginRespVo;

    }

    @GetMapping("/captcha")
    public Result<Map> getCaptchaCode(){
        return userService.getCaptchaCode();
    }

}
