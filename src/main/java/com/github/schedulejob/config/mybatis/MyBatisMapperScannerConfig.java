package com.github.schedulejob.config.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mapper scanner
 * @author: lvhao
 * @since: 2016-4-12 11:17
 */
@Configuration
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

        // 此处名称需和MyBatisConfig里 @Bean sqlSessionFactoryBean名称一样
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.github.schedulejob.mapper");
        return mapperScannerConfigurer;
    }

}
