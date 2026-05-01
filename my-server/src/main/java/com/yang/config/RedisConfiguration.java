package com.yang.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    //
    @Bean //解决 Redis 中 Key 序列化乱码问题
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建redis模版对象");
        RedisTemplate r=new RedisTemplate();
        r.setConnectionFactory(redisConnectionFactory);
        r.setKeySerializer(new StringRedisSerializer());
        return r;
    }


}
