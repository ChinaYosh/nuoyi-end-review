package com.ruoyi.web.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * OpenAPI 3.0 接口文档配置
 * 基于 Springdoc OpenAPI 实现，支持 Swagger UI
 * 
 * @author ruoyi
 */
@Configuration
public class SwaggerConfig
{
    /** 系统基础配置 */
    @Autowired
    private RuoYiConfig ruoyiConfig;
    
    /**
     * 自定义 OpenAPI 对象
     * 配置 API 文档的基本信息和安全认证
     */
    @Bean
    public OpenAPI customOpenApi()
    {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("Bearer", securityScheme()))
            .addSecurityItem(new SecurityRequirement().addList("Bearer"))
            .info(getApiInfo());
    }
    
    /**
     * 安全认证配置
     * 使用 Bearer Token 方式进行 API 认证
     */
    @Bean
    public SecurityScheme securityScheme()
    {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer")
            .name("Authorization")
            .in(SecurityScheme.In.HEADER)
            .description("请输入JWT认证Token");
    }
    
    /**
     * API 文档基本信息
     */
    private Info getApiInfo()
    {
        return new Info()
            .title("AIPel Hub 管理系统 - 接口文档")
            .description("AIPel Hub 多语言聚合平台 RESTful API 接口文档")
            .contact(new Contact()
                .name(ruoyiConfig.getName())
                .url("https://github.com/chinayosh/aipel-hub"))
            .version("v" + ruoyiConfig.getVersion());
    }
}
