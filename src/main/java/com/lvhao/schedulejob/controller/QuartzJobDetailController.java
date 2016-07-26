package com.lvhao.schedulejob.controller;

import com.lvhao.schedulejob.common.Response;
import com.lvhao.schedulejob.common.RetCodeConst;
import com.lvhao.schedulejob.domain.job.JobDetailDO;
import com.lvhao.schedulejob.service.QuartzJobDetailService;
import com.lvhao.schedulejob.util.ResponseBuilder;
import com.google.common.collect.Lists;
import org.quartz.JobKey;
import org.quartz.core.jmx.JobDataMapSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * quartz api
 *
 * @author: lvhao
 * @since: 2016-6-23 20:18
 */
@RestController
@RequestMapping("/jobs")
public class QuartzJobDetailController {

    @Autowired
    private QuartzJobDetailService quartzJobDetailService;

    /**
     * 任务列表
     * @return
     */
    @GetMapping
    public Response<List<JobDetailDO>> list(){
        List<JobDetailDO> jobDetailDOs = quartzJobDetailService.queryJobList();
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .withData(jobDetailDOs)
                .build();
    }

    /**
     * 查询指定jobKey jobDetail
     * @param name
     * @param group
     * @return
     */
    @GetMapping("/{group}/{name}")
    public Response<JobDetailDO> queryByJobKey(
            @PathVariable String name,
            @PathVariable String group){
        JobKey jobKey = new JobKey(name,group);
        JobDetailDO jobDetailDO = quartzJobDetailService.queryByKey(jobKey);
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .withData(jobDetailDO)
                .build();
    }

    /**
     * 添加任务
     * @param jobDetailDO
     * @return
     */
    @PostMapping
    public Response add(@RequestBody JobDetailDO jobDetailDO){
        boolean result = quartzJobDetailService.add(jobDetailDO);
        return ResponseBuilder.newResponse()
                .determineRetCodeByRetValue(result)
                .build();
    }

    /**
     * 批量删除Job
     * @param jobKeyGroups
     * @return
     */
    @DeleteMapping
    public Response delete(@RequestBody Map<String,List<String>> jobKeyGroups){
        List<JobKey> jobKeys = Lists.newArrayList();
        jobKeyGroups.forEach((k,v) ->
            v.forEach(name -> {
                JobKey jobKey = new JobKey(name,k);
                jobKeys.add(jobKey);
            })
        );
        boolean result = quartzJobDetailService.remove(jobKeys);
        return ResponseBuilder.newResponse()
                .determineRetCodeByRetValue(result)
                .build();
    }

    @PostMapping("/{group}/{name}")
    public Response triggerNow(@PathVariable String group,
                              @PathVariable String name,
                              @RequestBody Map<String,Object> jobData){
        JobKey jobKey = new JobKey(name,group);
        boolean result = quartzJobDetailService.triggerNow(
            jobKey,
            JobDataMapSupport.newJobDataMap(jobData)
        );
        return ResponseBuilder.newResponse()
                .determineRetCodeByRetValue(result)
                .build();
    }
}
