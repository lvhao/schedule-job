package com.lvhao.schedulejob.config.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mapper scanner
 *
 * @author: lvhao
 * @since: 2016-4-12 11:17
 */
@Configuration
@EnableConfigurationProperties(MybatisResourceConfig.class)
public class MyBatisMapperScannerConfig {

    @Autowired
    private MybatisResourceConfig mybatisResourceConfig;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

        mybatisResourceConfig.getMapper();
        // 此处名称需和MyBatisConfig里 @Bean sqlSessionFactoryBean名称一样
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.lvhao.schedulejob.mapper");
        return mapperScannerConfigurer;
    }

}
