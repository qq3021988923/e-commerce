package com.yang.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"}) // 生产环境(prod)不加载，避免接口暴露,
public class Knife4jConfig {

    public static final String ADMIN_TOKEN = "token";        // 管理端请求头
    public static final String USER_TOKEN = "authentication";// 用户端请求头

    @Bean
    public OpenAPI springShopOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("一门双至尊：后台+用户")
                        .description("这是基于Knife4j OpenApi3的测试接口文档")
                        .version("1.0版本")
                        .contact(new Contact()
                                .name("张三")
                                .email("3021988923@qq.com")))
                    // / 配置两个授权方案：token + authentication
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(ADMIN_TOKEN, new SecurityScheme()
                                .name(ADMIN_TOKEN)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("管理端 Token"))
                        .addSecuritySchemes(USER_TOKEN, new SecurityScheme()
                                .name(USER_TOKEN)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("用户端 Token")));

    }


    /**
     * 分组1：管理端接口（/admin/**）
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("管理端接口")
                .pathsToMatch("/admin/**")
                // 在不同的组里设置token值
                .addOperationCustomizer((operation, handlerMethod) ->
                        operation.addSecurityItem(new SecurityRequirement().addList(ADMIN_TOKEN)))
                .build();
    }

    /**
     * 分组2：用户端接口（/user/**）
     */
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户端接口")
                .pathsToMatch("/user/**")

                .addOperationCustomizer((operation, handlerMethod) ->
                        operation.addSecurityItem(new SecurityRequirement().addList(USER_TOKEN)))
                .build();
    }

}