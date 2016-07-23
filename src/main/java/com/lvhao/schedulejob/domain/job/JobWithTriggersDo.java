package com.lvhao.schedulejob.domain.job;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 任务抽象业务类
 *
 * @author: lvhao
 * @since: 2016-6-23 20:43
 */
public class JobWithTriggersDo {

    // job info
    private JobDo jobDo;

    // trigger info
    private Set<TriggerDo> triggerDos;

    // 处理job
    public transient Consumer<JobDetail> fillWithQuartzJobDetail = jd -> {
        jobDo = new JobDo();

        // job
        JobKey jk = jd.getKey();

        // name group desc
        BeanUtils.copyProperties(jk,jobDo);
        jobDo.setTargetClass(jd.getJobClass().getCanonicalName());

        // ext
        JobDataMap jdm= jd.getJobDataMap();
        if (Objects.nonNull(jdm)) {
            jobDo.setExtInfo(jdm.getWrappedMap());
        }

        this.setJobDo(jobDo);
    };

    // 处理triggers
    public transient Consumer<List<Trigger>> fillWithQuartzTriggers = trList -> {

        // triggers
        Set<TriggerDo> tdSet = trList.stream().map(tr ->{
            TriggerDo td = new TriggerDo();
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
        this.setTriggerDos(tdSet);
    };

    public JobDo getJobDo() {
        return jobDo;
    }

    public void setJobDo(JobDo jobDo) {
        this.jobDo = jobDo;
    }

    public Set<TriggerDo> getTriggerDos() {
        return triggerDos;
    }

    public void setTriggerDos(Set<TriggerDo> triggerDos) {
        this.triggerDos = triggerDos;
    }

}
