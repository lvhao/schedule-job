package com.github.schedulejob.controller;

import com.google.common.collect.Lists;

import com.github.schedulejob.domain.job.JobDetailDO;
import com.github.schedulejob.service.QuartzJobDetailService;

import org.quartz.JobKey;
import org.quartz.core.jmx.JobDataMapSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * quartz api
 *
 * @author: lvhao
 * @since: 2016-6-23 20:18
 */
@Api(tags = "job")
@RestController
@RequestMapping("/jobs")
public class QuartzJobDetailController {

    @Autowired
    private QuartzJobDetailService quartzJobDetailService;

    @ApiOperation(value="获取任务列表")
    @GetMapping
    public ResponseEntity<List<JobDetailDO>> list(){
        List<JobDetailDO> jobDetailDOs = quartzJobDetailService.queryJobList();
        return ResponseEntity.ok().body(jobDetailDOs);
    }

    @ApiOperation("查询指定jobKey jobDetail")
    @ApiImplicitParams({
        @ApiImplicitParam(name="group",value="组名",required = true,dataType = "String",
                paramType="path"),
        @ApiImplicitParam(name="name",value="名称",required = true,dataType = "String",
                paramType="path")
    })
    @GetMapping("/{group}/{name}")
    public ResponseEntity<JobDetailDO> queryByJobKey(
            @PathVariable String name,
            @PathVariable String group){
        JobKey jobKey = new JobKey(name,group);
        JobDetailDO jobDetailDO = quartzJobDetailService.queryByKey(jobKey);
        return ResponseEntity.ok().body(jobDetailDO);
    }

    @ApiOperation("添加任务Job")
    @PostMapping
    public ResponseEntity<Boolean> add(@RequestBody JobDetailDO jobDetailDO){
        boolean result = quartzJobDetailService.add(jobDetailDO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @ApiOperation("批量删除Job")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "jobKeyGroups",value = "批量删除的任务")
    })
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestBody Map<String,List<String>> jobKeyGroups){
        List<JobKey> jobKeys = Lists.newArrayList();
        jobKeyGroups.forEach((k,v) ->
            v.forEach(name -> {
                JobKey jobKey = new JobKey(name,k);
                jobKeys.add(jobKey);
            })
        );
        boolean result = quartzJobDetailService.remove(jobKeys);
        return ResponseEntity.ok().body(result);
    }

    @ApiOperation("立即触发任务")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "group",value = "组名",required = true,dataType = "String",
                paramType = "path"),
        @ApiImplicitParam(name = "name",value = "任务名",required = true,dataType = "String",
                paramType = "path"),
        @ApiImplicitParam(name = "jobData",value = "额外数据",required = true,
                dataType = "Map<String,Object>",
                paramType = "body")
    })
    @PostMapping("/{group}/{name}")
    public ResponseEntity<Boolean> triggerNow(@PathVariable String group,
                              @PathVariable String name,
                              @RequestBody Map<String,Object> jobData){
        JobKey jobKey = new JobKey(name,group);
        boolean result = quartzJobDetailService.triggerNow(
            jobKey,
            JobDataMapSupport.newJobDataMap(jobData)
        );
        return ResponseEntity.ok().body(result);
    }
}
