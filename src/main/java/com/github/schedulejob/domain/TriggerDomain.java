package com.github.schedulejob.domain;

import org.quartz.*;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import java.text.ParseException;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-6-23 20:59
 */
public class TriggerDomain {

    // trigger info
    private String name;
    private String groupName;
    private String cronExpression;
    private String description;

    public CronTrigger convert2QuartzTrigger(JobDetail jobDetail){
        CronExpression ce = null;
        try {
            ce= new CronExpression("0/20 * * * * ?");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule(ce))
                .withIdentity(this.name,this.groupName)
                .withDescription(this.description)
                .build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TriggerDomain{");
        sb.append("name='").append(name).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", cronExpression='").append(cronExpression).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
