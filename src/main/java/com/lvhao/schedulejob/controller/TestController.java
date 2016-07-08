package com.lvhao.schedulejob.controller;

import com.alibaba.fastjson.JSONObject;
import com.lvhao.schedulejob.common.Response;
import com.lvhao.schedulejob.util.ResponseUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用路由
 *
 * @author: lvhao
 * @since: 2016-7-8 14:36
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value="/{code}/{msg}",method = RequestMethod.GET)
    public Response test(@PathVariable String code, @PathVariable String msg){
        JSONObject jo = new JSONObject();
        jo.put("code",code);
        jo.put("msg",msg);
        return ResponseUtils.buildRetCodeOfLocalResponse(jo);
    }
}
