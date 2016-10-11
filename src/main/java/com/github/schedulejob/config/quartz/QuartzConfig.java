package com.github.schedulejob.config.quartz;

import com.github.schedulejob.config.ExternalPathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

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
    private ExternalPathConfig externalPathConfig;

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
    public SchedulerFactoryBean init(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setTransactionManager(platformTransactionManager);
        schedulerFactoryBean.setQuartzProperties(externalPathConfig.quartzCfg());

        schedulerFactoryBean.setAutoStartup(true);

        // 覆盖已存在定时任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(false);

        schedulerFactoryBean.setJobFactory(autowiringQuartzJobFactory);
        return schedulerFactoryBean;
    }

}
