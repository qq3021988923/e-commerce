package com.yang;

import com.yang.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class DemoController {


    // http://localhost:1234/demo
    @RequestMapping("/demo")
    public Object demo(){
        HashMap<String, Object> c = new HashMap<>();
        c.put("id", "1");
        // 1. 生成JWT令牌并接收返回值
       // String jwtToken = JwtUtil.createJWT("66666666666666666666666666666666", 30000L, c);
         //2. 打印令牌（方便测试）
       // System.out.println("生成JWT令牌：" + jwtToken);
//        Claims claims = JwtUtil.parseJwt("66666666666666666666666666666666", "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJleHAiOjE3NzM5MDU4Nzd9.q2FwDMil-RXunNfDl1C27My7228XTx9wIrEIFd47r_8");
//        System.out.println("解析jwt令牌"+claims);
        return c;
    }
}
