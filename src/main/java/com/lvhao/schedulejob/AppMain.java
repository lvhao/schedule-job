package com.lvhao.schedulejob;

import com.lvhao.schedulejob.config.mybatis.MybatisResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 程序入口
 *
 * @author: lvhao
 * @since: 2016-4-12 13:27
 */
@SpringBootApplication
@EnableConfigurationProperties({MybatisResourceConfig.class})
public class AppMain {
    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }
}
