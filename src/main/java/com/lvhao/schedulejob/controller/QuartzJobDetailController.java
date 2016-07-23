package com.lvhao.schedulejob.controller;

import com.lvhao.schedulejob.common.Response;
import com.lvhao.schedulejob.common.RetCodeConst;
import com.lvhao.schedulejob.domain.job.JobWithTriggersDo;
import com.lvhao.schedulejob.service.QuartzJobDetailService;
import com.lvhao.schedulejob.util.ResponseBuilder;
import com.google.common.collect.Lists;
import org.quartz.JobKey;
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
    public Response<List<JobWithTriggersDo>> list(){
        List<JobWithTriggersDo> jobWithTriggersDoList = quartzJobDetailService.queryJobList();
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .withData(jobWithTriggersDoList)
                .build();
    }

    /**
     * 查询指定jobKey jobDetail
     * @param name
     * @param groupName
     * @return
     */
    @GetMapping("/{groupName}/{name}")
    public Response<JobWithTriggersDo> queryByJobKey(
            @PathVariable String name,
            @PathVariable String groupName){
        JobKey jobKey = new JobKey(name,groupName);
        JobWithTriggersDo jobWithTriggersDo = quartzJobDetailService.queryByKey(jobKey);
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .withData(jobWithTriggersDo)
                .build();
    }

    /**
     * 添加任务
     * @param jobWithTriggersDo
     * @return
     */
    @PostMapping
    public Response add(@RequestBody JobWithTriggersDo jobWithTriggersDo){
        boolean result = quartzJobDetailService.add(jobWithTriggersDo);
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
}
