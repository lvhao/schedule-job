package com.lvhao.schedulejob.domain.job;

import com.lvhao.schedulejob.common.AppConst;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

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
public class JobWithTriggersDO {

    // job info
    private JobDO jobDO;

    // trigger info
    private Set<TriggerDO> triggerDOSet;

    // 处理job
    public transient Consumer<JobDetail> fillWithQuartzJobDetail = jd -> {
        // ext
        JobDataMap jdm= jd.getJobDataMap();
        String type = String.valueOf(jdm.get("type"));
        if (Objects.equals(type, AppConst.JobType.HTTP_JOB)) {
            jobDO = new HttpJobDO();
            String url = String.valueOf(jdm.get("url"));
            String method = String.valueOf(jdm.get("method"));
            String jsonParams = String.valueOf(jdm.get("jsonParams"));
            ((HttpJobDO)jobDO).setType(type);
            ((HttpJobDO)jobDO).setUrl(url);
            ((HttpJobDO)jobDO).setMethod(method);
            ((HttpJobDO)jobDO).setJsonParams(jsonParams);

        } else {
            jobDO = new ThriftJobDO();
            String method = String.valueOf(jdm.get("method"));
            String thriftIface = String.valueOf(jdm.get("thriftIface"));
            ((ThriftJobDO)jobDO).setType(type);
            ((ThriftJobDO)jobDO).setThriftIface(thriftIface);
            ((ThriftJobDO)jobDO).setMethod(method);
        }

        // job
        JobKey jk = jd.getKey();
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

}
