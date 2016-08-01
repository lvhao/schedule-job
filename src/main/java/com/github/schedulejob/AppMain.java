package com.github.schedulejob;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 程序入口
 *
 * @author: lvhao
 * @since: 2016-4-12 13:27
 */
@SpringBootApplication
public class AppMain {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(AppMain.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .run(args);
    }
}
