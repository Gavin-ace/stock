package com.lzj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class TestAll {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPwd(){
        String pwd="123";
        String encode=passwordEncoder.encode(pwd);
        System.out.println(encode);


        boolean flag=passwordEncoder.matches(pwd,encode);
        System.out.println(flag);
    }
}
