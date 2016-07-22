package com.lvhao.schedulejob.controller;

import com.alibaba.fastjson.JSONObject;
import com.lvhao.schedulejob.common.Response;
import com.lvhao.schedulejob.util.ResponseUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 测试用路由
 *
 * @author: lvhao
 * @since: 2016-7-8 14:36
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/{code}/{msg}")
    public Response test(@PathVariable String code, @PathVariable String msg){
        JSONObject jo = new JSONObject();
        jo.put("code",code);
        jo.put("msg",msg);
        return ResponseUtils.buildRetCodeOfLocalResponse(jo);
    }
}
