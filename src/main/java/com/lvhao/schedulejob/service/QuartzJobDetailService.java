package com.lvhao.schedulejob.service;

import com.lvhao.schedulejob.domain.JobWithTriggersDO;
import com.google.common.collect.Lists;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 定时任务操作
 *
 * @author: lvhao
 * @since: 2016-6-23 19:58
 */
@Service
public class QuartzJobDetailService extends BaseService {

    // SchedulerFactoryBean 创建
    @Autowired
    private Scheduler scheduler;

    // 任务列表
    public List<JobWithTriggersDO> queryJobList(){
        List<JobWithTriggersDO> jobWithTriggersDOList = Lists.newArrayList();

        // 数据处理
        Function<Set<JobKey>,List<JobWithTriggersDO>> copyPropFun = jbst -> {
            List<JobWithTriggersDO> jddList = Lists.newArrayList();
            jddList = jbst.stream().map(jk ->{
                JobDetail jd = null;
                List<Trigger> trList = this.getTriggerByKey(jk);
                jd = this.getJobDetailByKey(jk);

                // jobDetail
                JobWithTriggersDO jobWithTriggersDO = new JobWithTriggersDO();
                jobWithTriggersDO.fillWithQuartzJobDetail.accept(jd);
                jobWithTriggersDO.fillWithQuartzTriggers.accept(trList);
                return jobWithTriggersDO;
            }).collect(Collectors.toList());
            return jddList;
        };

        try {
            Set<JobKey> jobSet = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            jobWithTriggersDOList = copyPropFun.apply(jobSet);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobWithTriggersDOList;
    }

    /**
     * 查询指定jobkey jobDetail
     * @param jobKey
     * @return
     */
    public JobWithTriggersDO queryByKey(JobKey jobKey){
        JobWithTriggersDO jobWithTriggersDO = new JobWithTriggersDO();
        JobDetail jobDetail = this.getJobDetailByKey(jobKey);
        List<Trigger> triggerList = this.getTriggerByKey(jobKey);
        jobWithTriggersDO.fillWithQuartzJobDetail.accept(jobDetail);
        jobWithTriggersDO.fillWithQuartzTriggers.accept(triggerList);
        return jobWithTriggersDO;
    }

    /**
     * 添加任务
     * @param jobWithTriggersDO
     */
    public void add(JobWithTriggersDO jobWithTriggersDO){
        JobDetail jobDetail = jobWithTriggersDO.getJobDO().convert2QuartzJobDetail();
        Set<CronTrigger> triggerSet = jobWithTriggersDO.getTriggerDOSet().stream().map(td ->
            td.convert2QuartzTrigger(jobDetail)
        ).collect(Collectors.toSet());
        try {

            // 如果已经存在 则替换
            scheduler.scheduleJob(jobDetail,triggerSet,true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除任务
     *
     * @param jobKeyList
     */
    public void remove(List<JobKey> jobKeyList){
        try {
            scheduler.deleteJobs(jobKeyList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 停用任务
    public void disable(GroupMatcher<JobKey> matcher){
        try {
            scheduler.pauseJobs(matcher);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 停用所有任务
    public void disableAll(){
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 启用任务
    public void enable(GroupMatcher<JobKey> matcher){
        try {
            scheduler.resumeJobs(matcher);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 启用所有任务
    public void enableAll(){
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 立即触发任务
    public void triggerNow(JobKey jobKey, JobDataMap jobDataMap){
        try {
            scheduler.triggerJob(jobKey,jobDataMap);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key 获取jobDetail
     * @param jobKey
     * @return
     */
    public JobDetail getJobDetailByKey(JobKey jobKey){
        JobDetail jd = null;
        try {
            jd = scheduler.getJobDetail(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jd;
    }

    /**
     * 根据key 获取job trigger
     * @param jobKey
     * @return
     */
    public List<Trigger> getTriggerByKey(JobKey jobKey){
        List<Trigger> triggerList = Lists.newArrayList();
        try {
            triggerList = (List<Trigger>)scheduler.getTriggersOfJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return triggerList;
    }
}
