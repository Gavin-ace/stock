package com.lzj.vo.resp;

import lombok.Data;


/**
 * 登录请求响应数据
 */
@Data
public class LoginRespVo {

    /**
     *用户ID
     * 将long类型数字进行json格式转化时，转成String格式类型
     */
    private long id;

    /**
     * 电话
     */
    private String username;

    /**
     * 用户名
     */
    private String nickName;

    /**
     * 昵称
     */
    private String phone;


}
