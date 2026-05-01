package com.yang.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    // 生成带自定义信息、过期时间的 JWT 令牌（HS256 算法加密）
    public static String createJWT(String key, long ttlMillis, Map<String,Object> claims){
        // 指定签名的时候使用的签名算法，header那部分
        SignatureAlgorithm signatureAlgorithm= SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long expMillis=System.currentTimeMillis()+ttlMillis;
        Date exp=new Date(expMillis);

        JwtBuilder builder= Jwts.builder()
                .setClaims(claims) // 把自定义信息（如用户id,手机号）存入令牌
                //设置签名使用的签名算法和自定义签名使用的密钥
                .signWith(signatureAlgorithm,key.getBytes(StandardCharsets.UTF_8))
                .setExpiration(exp); //设置过期时间 时间一到 解析失败
        return builder.compact(); // 生成jwt字符串
    }

    // 解析token 没指定算法，会使用 JJWT 库的默认值（HS256）
    public static Claims parseJwt(String key, String token){
        Claims claims=Jwts.parser()
                // 和生成令牌时的密钥一致
                .setSigningKey(key.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();// 解析令牌，验证签名-》通过后获取令牌的自定义信息
        return claims;//返回生成token是自定义的信息
    }

}
