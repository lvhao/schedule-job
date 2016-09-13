package com.github.schedulejob.config.quartz;

import com.github.schedulejob.config.PathCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Quartz配置类
 *
 * @author: lvhao
 * @since: 2016-6-2 20:09
 */
@Configuration
public class QuartzConfig {
    private static final Logger log = LoggerFactory.getLogger(QuartzConfig.class);

    @Autowired
    private PathCfg pathCfg;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private AutowiringQuartzJobFactory autowiringQuartzJobFactory;

    @PostConstruct
    public void initDone() {
        log.info("Quartz init done...");
    }

    @Bean
    @ConfigurationProperties(prefix = "org.quartz")
    public Properties quartzProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        PathMatchingResourcePatternResolver prpr = new PathMatchingResourcePatternResolver();
        Resource resource = prpr.getResource(pathCfg.getQuartzCfg());
        propertiesFactoryBean.setLocation(resource);
        Properties properties = null;
        try {
            propertiesFactoryBean.afterPropertiesSet();
            properties = propertiesFactoryBean.getObject();

        } catch (IOException e) {
            log.error("读取quartz配置文件出错!",e);
        }
        return properties;
    }

    @Bean
    public SchedulerFactoryBean init(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setTransactionManager(platformTransactionManager);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());

        schedulerFactoryBean.setAutoStartup(true);

        // 覆盖已存在定时任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);

        schedulerFactoryBean.setJobFactory(autowiringQuartzJobFactory);
        return schedulerFactoryBean;
    }

}
