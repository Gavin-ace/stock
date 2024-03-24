package com.lzj.service;

import com.lzj.pojo.entity.SysUser;
import com.lzj.vo.req.LoginReqVo;
import com.lzj.vo.resp.LoginRespVo;
import com.lzj.vo.resp.Result;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserService {

    SysUser getUserByName(String userName);

    Result<LoginRespVo> login(LoginReqVo loginReqVo);

    Result<Map> getCaptchaCode();

}
