package com.lvhao.schedulejob.controller;

import com.google.common.collect.Lists;
import com.lvhao.schedulejob.common.Response;
import com.lvhao.schedulejob.common.RetCodeConst;
import com.lvhao.schedulejob.domain.job.JobDetailDO;
import com.lvhao.schedulejob.service.QuartzJobDetailService;
import com.lvhao.schedulejob.util.PageBuilder;
import com.lvhao.schedulejob.util.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.quartz.JobKey;
import org.quartz.core.jmx.JobDataMapSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * quartz api
 *
 * @author: lvhao
 * @since: 2016-6-23 20:18
 */
@Api("quartz 任务API")
@RestController
@RequestMapping("/jobs")
public class QuartzJobDetailController {

    @Autowired
    private QuartzJobDetailService quartzJobDetailService;

    @ApiOperation("获取任务列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query",name = "index",dataType = "int"),
        @ApiImplicitParam(paramType = "query",name = "size",dataType = "int")
    })
    @ApiResponses({
        @ApiResponse(code=400,message="请求参数没填好"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping
    public Response<List<JobDetailDO>> list(){
        List<JobDetailDO> jobDetailDOs = quartzJobDetailService.queryJobList();
        return ResponseBuilder.newResponse()
                .wittPage(PageBuilder.DEFAULT_PAGE_INFO)
                .withRetCode(RetCodeConst.OK)
                .withData(jobDetailDOs)
                .build();
    }

    @ApiOperation("查询指定jobKey jobDetail")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="path",name="group",value="组名",dataType = "string"),
        @ApiImplicitParam(paramType="path",name="name",value="名称",dataType = "string")
    })
    @ApiResponses({
        @ApiResponse(code=200, message = "",response = Response.class)
    })
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
                .withRetCodeBy(result)
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
                .withRetCodeBy(result)
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
                .withRetCodeBy(result)
                .build();
    }
}
