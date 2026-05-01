package com.yang.interceptor;

import com.yang.constant.JwtClaimsConstant;
import com.yang.context.BaseContext;
import com.yang.properties.JwtProperties;
import com.yang.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j // api访问之前 需要在这里做校验拦截 配置WebMvcConfiguration一起使用
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
     // 过滤非 Controller 方法的请求（如静态资源、Swagger 文档
      if(!(handler instanceof HandlerMethod)){

          return true;
      }


      try { // 管理端token校验原代码 现在使用一门双至尊
          String token = request.getHeader(jwtProperties.getAdminTokenName());

          log.info("jwt校验:{}",token);

          // 如果签名不对 或者时间过期，就不能解析出来
          Claims claims = JwtUtil.parseJwt(jwtProperties.getAdminSecretKey(), token);
          Long id = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
          log.info("当前账号id:{}",id);
          BaseContext.setCurrentId(id);

          return true;

      }catch(Exception e){
          response.setStatus(401);
          return false;
      }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId(); //每次请求结束，无论成功还是失败，都清空ThreadLocal
    }
}

