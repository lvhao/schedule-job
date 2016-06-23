package com.github.schedulejob.config.quartz;

import com.github.schedulejob.schedule.QuartzTestJob;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
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
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private AutowiringQuartzJobFactory autowiringQuartzJobFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void initDone() {
        log.info("Quartz init done...");
    }

    @Bean
    public Properties quartzProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        Properties properties = null;
        try {
            propertiesFactoryBean.afterPropertiesSet();
            properties = propertiesFactoryBean.getObject();

        } catch (IOException e) {
            log.error("读取quartz配置文件出错!",e);
        }
        return properties;
    }

    /*@Bean
    public JobDetailFactoryBean processJob() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean ();
        jobDetailFactoryBean.setJobClass(QuartzTestJob.class);
        jobDetailFactoryBean.setGroup("MM");
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean processTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(processJob().getObject());
        cronTriggerFactoryBean.setCronExpression("0/20 * * * * ?");
        cronTriggerFactoryBean.setGroup("MM");
        return cronTriggerFactoryBean;
    }*/

    @Bean
    public SchedulerFactoryBean init(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setApplicationContext(applicationContext);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setTransactionManager(platformTransactionManager);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());

        schedulerFactoryBean.setAutoStartup(true);

        // schedule启动后30S 开始任务
        schedulerFactoryBean.setStartupDelay(30);

        // 覆盖已存在定时任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);

        schedulerFactoryBean.setJobFactory(autowiringQuartzJobFactory);
//        Trigger[] triggers = { processTrigger().getObject() };
//        schedulerFactoryBean.setTriggers(triggers);
        return schedulerFactoryBean;
    }

}
