package com.github.schedulejob.config.okhttp3;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * OkHttp3Client 配置
 * Created by root on 2016/7/22 0022.
 */
@Configuration
public class OkHttp3ClientConfig {

    @Bean
    public OkHttpClient okHttpClient(){

        // 缺省 带有连接池
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();
    }
}
