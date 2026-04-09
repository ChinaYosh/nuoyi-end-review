package com.ruoyi.framework.config;

import com.alibaba.druid.support.jakarta.StatViewServlet;
import com.alibaba.druid.support.jakarta.WebStatFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Druid 监控配置
 * 手动配置 Druid 监控页面和统计过滤器
 * 
 * @author ruoyi
 */
@Configuration
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true")
public class DruidMonitorConfig
{
    /**
     * Druid 监控页面 Servlet
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet()
    {
        ServletRegistrationBean<StatViewServlet> registrationBean = 
            new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        
        Map<String, String> initParams = new HashMap<>();
        // IP白名单 (为空表示允许所有)
        initParams.put("allow", "");
        // 控制台管理用户名
        initParams.put("loginUsername", "ruoyi");
        // 控制台管理密码
        initParams.put("loginPassword", "123456");
        // 是否启用重置按钮
        initParams.put("resetEnable", "true");
        
        registrationBean.setInitParameters(initParams);
        return registrationBean;
    }

    /**
     * Druid Web 统计过滤器
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> druidWebStatFilter()
    {
        FilterRegistrationBean<WebStatFilter> registrationBean = 
            new FilterRegistrationBean<>(new WebStatFilter());
        
        Map<String, String> initParams = new HashMap<>();
        // 排除静态资源和接口
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        
        registrationBean.setInitParameters(initParams);
        registrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return registrationBean;
    }
}
