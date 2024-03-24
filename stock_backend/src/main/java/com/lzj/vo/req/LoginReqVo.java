package com.lzj.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 登录请求vo
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReqVo {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;


    /**
     * 保存redis生成的sessionId
     */
    private String sessionId;
}
