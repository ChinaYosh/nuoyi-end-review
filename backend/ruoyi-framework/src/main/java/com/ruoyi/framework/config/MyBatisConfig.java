package com.ruoyi.framework.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

/**
 * MyBatis-Plus 手动配置
 * 适配 Spring Boot 4.x，替代自动配置
 * 
 * @author ruoyi
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig
{
    /**
     * SqlSessionFactory 配置（懒加载）
     */
    @Lazy
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception
    {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        
        // 设置 Mapper XML 文件路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/**/*Mapper.xml"));
        
        // 设置类型别名包
        factoryBean.setTypeAliasesPackage("com.ruoyi.**.domain");
        
        return factoryBean.getObject();
    }

    /**
     * SqlSessionTemplate 配置（懒加载）
     */
    @Lazy
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事务管理器配置（懒加载）
     */
    @Lazy
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }
}
