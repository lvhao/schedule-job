package com.lvhao.schedulejob.domain;

import org.quartz.*;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 任务抽象业务类
 *
 * @author: lvhao
 * @since: 2016-6-23 20:43
 */
public class JobWithTriggersDO {

    // job info
    private JobDO jobDO;

    // trigger info
    private Set<TriggerDO> triggerDOSet;

    // 处理job
    public transient Consumer<JobDetail> fillWithQuartzJobDetail = jd -> {
        // job
        JobKey jk = jd.getKey();
        JobDO jobDO = new JobDO();
        jobDO.setName(jk.getName());
        jobDO.setGroup(jk.getGroup());
        jobDO.setTargetClass(jd.getJobClass().getCanonicalName());
        jobDO.setDescription(jd.getDescription());
        this.setJobDO(jobDO);
    };

    // 处理triggers
    public transient Consumer<List<Trigger>> fillWithQuartzTriggers = trList -> {

        // triggers
        Set<TriggerDO> tdSet = trList.stream().map(tr ->{
            TriggerDO td = new TriggerDO();
            if (tr instanceof CronTrigger) {
                CronTrigger ctr = (CronTrigger) tr;
                td.setCronExpression(ctr.getCronExpression());
            }
            TriggerKey trk = tr.getKey();
            td.setName(trk.getName());
            td.setGroup(trk.getGroup());
            td.setDescription(tr.getDescription());
            return td;
        }).collect(Collectors.toSet());
        this.setTriggerDOSet(tdSet);
    };

    public JobDO getJobDO() {
        return jobDO;
    }

    public void setJobDO(JobDO jobDO) {
        this.jobDO = jobDO;
    }

    public Set<TriggerDO> getTriggerDOSet() {
        return triggerDOSet;
    }

    public void setTriggerDOSet(Set<TriggerDO> triggerDOSet) {
        this.triggerDOSet = triggerDOSet;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobWithTriggersDO{");
        sb.append("jobDO=").append(jobDO);
        sb.append(", triggerDOSet=").append(triggerDOSet);
        sb.append('}');
        return sb.toString();
    }
}
