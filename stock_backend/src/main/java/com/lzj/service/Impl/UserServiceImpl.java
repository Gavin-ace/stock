package com.lzj.service.Impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.lzj.constant.StockConstant;
import com.lzj.pojo.entity.SysUser;
import com.lzj.mapper.SysUserMapper;
import com.lzj.utils.IdWorker;
import com.lzj.service.UserService;
import com.lzj.vo.req.LoginReqVo;
import com.lzj.vo.resp.LoginRespVo;
import com.lzj.vo.resp.ResponseCode;
import com.lzj.vo.resp.Result;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SysUser getUserByName(String UserName) {
        return sysUserMapper.findByUserName(UserName);
    }

    @Override
    public Result<LoginRespVo> login(LoginReqVo loginReqVo) {
        //判断用户密码是否为空
        if(loginReqVo==null|| StringUtils.isBlank(loginReqVo.getUsername())||StringUtils.isBlank(loginReqVo.getPassword())){
            return Result.error(ResponseCode.DATA_ERROR.getMessage());
        }
        SysUser sysUser=sysUserMapper.findByUserName(loginReqVo.getUsername());
        //判断用户是否存在,比对密码
        if(sysUser==null||!passwordEncoder.matches(loginReqVo.getPassword(),sysUser.getPassword())){
            return Result.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
        }


//        //判断验证码是否为空
//        if(StringUtils.isBlank(loginReqVo.getSessionId())){
//            return Result.error(ResponseCode.CHECK_CODE_NOT_EMPTY);
//        }
//        //判断验证码是否正确
//        if(loginReqVo.getSessionId()!=redisTemplate.opsForValue().get("sessionId")){
//            return Result.error(ResponseCode.CHECK_CODE_ERROR);
//        }

        //3.根据Rkey从redis中获取缓存的校验码
        String rCode= (String) redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX+loginReqVo.getSessionId());
        //判断获取的验证码是否存在，以及是否与输入的验证码相同
        if (StringUtils.isBlank(rCode) || ! rCode.equalsIgnoreCase(loginReqVo.getCode())) {
            //验证码输入有误
            return Result.error(ResponseCode.CHECK_CODE_ERROR);
        }


        //组装登录成功数据
        LoginRespVo respVo=new LoginRespVo();
        BeanUtils.copyProperties(sysUser,respVo);

        return Result.ok(respVo);
    }

    @Override
    public Result<Map> getCaptchaCode() {

        //生成验证码
        LineCaptcha captcha= CaptchaUtil.createLineCaptcha(200,30,4,3);

        //设置背景颜色清灰
        captcha.setBackground(Color.lightGray);
       //自定义校验码生成方式
//        captcha.setGenerator(new CodeGenerator() {
//            @Override
//            public String generate() {
//                return RandomStringUtils.randomAlphabetic(4);
//            }
//
//            @Override
//            public boolean verify(String s, String s1) {
//                return s.equalsIgnoreCase(s1);
//            }
//        });
        String checkCode=captcha.getCode();
        //生成sessionId
        String sessionId=String.valueOf(idWorker.nextId());
        //将sessionId存入redis，并设置存活时间为1分钟
        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX+sessionId,checkCode,1, TimeUnit.MINUTES);
        HashMap<String, String> info = new HashMap<>();
        info.put("sessionId",sessionId);
        info.put("imgData",captcha.getImageBase64());
        return Result.ok(info);


//        //1.生成sessionId  rkey  基于雪花算法生成的id比较长（19），如果直接以long类型响应给前端，会导致浏览器获取数据时丢失精度
//        long sessionId = idWorker.nextId();
//        String sessionStrId = String.valueOf(sessionId);
//        //2.生成随机校验码 长度是4，纯数字
//        String checkCode = RandomStringUtils.randomNumeric(4);
//        //3.将校验码保存在session下-》保存在redis下
//        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX +sessionStrId,checkCode,2, TimeUnit.MINUTES);
//        //4.响应前端
//        Map<String,String> info=new HashMap<>();
//        info.put("code",checkCode);
//        info.put("rkey",sessionStrId);
//        return Result.ok(info);
    }
}
