package com.yang;

import com.yang.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@SpringBootApplication
@Slf4j
@EnableScheduling // 开启定时任务
@EnableCaching // 开启缓存注解支持
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class,args);
        log.info("server 启动成功"+
                "\n"+"接口文档访问地址\n"+
                "本地Knife4j地址:   http://localhost:1234/doc.html"
        );
    }

}