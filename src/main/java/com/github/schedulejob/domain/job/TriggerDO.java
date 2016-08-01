package com.github.schedulejob.domain.job;

import com.google.common.base.Strings;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.TriggerBuilder;

import java.text.ParseException;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 触发器域
 *
 * @author: lvhao
 * @since: 2016-6-23 20:59
 */
public class TriggerDO {

    // trigger info
    private String name;
    private String group;
    private String cronExpression;
    private String description;

    public CronTrigger convert2QuartzTrigger(JobDetail jobDetail){
        CronExpression ce = null;
        try {
            checkArgument(!Strings.isNullOrEmpty(cronExpression),"cronExpression参数非法");
            ce= new CronExpression(this.cronExpression);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule(ce))
                .withIdentity(this.name,this.group)
                .withDescription(this.description)
                .build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
        final StringBuffer sb = new StringBuffer("TriggerDO{");
        sb.append("name='").append(name).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", cronExpression='").append(cronExpression).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
