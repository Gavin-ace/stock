package com.lzj.config;


import com.lzj.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @Author by lizhijun
 * 定义公共配置类
 */
@Configuration
public class CommonConfig {


    /**
     * 密码加密器
     * BCrytPasswordEncoder方法采用SHA-256对密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置id生成器bean
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1l,2l);
    }

}
