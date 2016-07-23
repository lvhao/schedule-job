package com.lvhao.schedulejob.config.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mapper scanner
 *
 * @author: lvhao
 * @since: 2016-4-12 11:17
 */
@Configuration
public class MyBatisMapperScannerConfig {

    public static final String BASE_PACKAGE = "com.lvhao.schedulejob.mapper";
    public static final String SQL_SESSION_FACTORY_BEAN_NAME = "sqlSessionFactoryBean";

    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

        // 此处名称需和MyBatisConfig里 @Bean sqlSessionFactoryBean名称一样
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY_BEAN_NAME);
        mapperScannerConfigurer.setBasePackage(BASE_PACKAGE);
        return mapperScannerConfigurer;
    }

}
