package com.github.schedulejob.domain;

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
public class JobWithTriggersDomain {

    // job info
    private JobDomain jobDomain;

    // trigger info
    private Set<TriggerDomain> triggerDomainSet;

    // 处理job
    public Consumer<JobDetail> fillWithQuartzJobDetail = jd -> {
        // job
        JobKey jk = jd.getKey();
        JobDomain jobDomain = new JobDomain();
        jobDomain.setName(jk.getName());
        jobDomain.setGroupName(jk.getGroup());
        jobDomain.setTargetClass(jd.getJobClass().getCanonicalName());
        jobDomain.setDescription(jd.getDescription());
        this.setJobDomain(jobDomain);
    };

    // 处理triggers
    public Consumer<List<Trigger>> fillWithQuartzTriggers = trList -> {

        // triggers
        Set<TriggerDomain> tdSet = trList.stream().map(tr ->{
            TriggerDomain td = new TriggerDomain();
            if (tr instanceof CronTrigger) {
                CronTrigger ctr = (CronTrigger) tr;
                td.setCronExpression(ctr.getCronExpression());
            }
            TriggerKey trk = tr.getKey();
            td.setName(trk.getName());
            td.setGroupName(trk.getGroup());
            td.setDescription(tr.getDescription());
            return td;
        }).collect(Collectors.toSet());
        this.setTriggerDomainSet(tdSet);
    };

    public JobDomain getJobDomain() {
        return jobDomain;
    }

    public void setJobDomain(JobDomain jobDomain) {
        this.jobDomain = jobDomain;
    }

    public Set<TriggerDomain> getTriggerDomainSet() {
        return triggerDomainSet;
    }

    public void setTriggerDomainSet(Set<TriggerDomain> triggerDomainSet) {
        this.triggerDomainSet = triggerDomainSet;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobWithTriggersDomain{");
        sb.append("jobDomain=").append(jobDomain);
        sb.append(", triggerDomainSet=").append(triggerDomainSet);
        sb.append('}');
        return sb.toString();
    }
}
