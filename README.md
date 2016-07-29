# schedule-job
##### 项目采用 __Spring Boot__ 框架
> * 集成了分布式任务调度框架 __Quartz__
> * 使用SpringMVC作为路由控制，JSON 数据解析使用 __fastjson__ 替换了 jackson。
> * HTTP接口调用使用 __OKhttp3__ 替代了 HttpClient。
> * __Thrift__ 调用实现了client池化管理。
> * 数据持久层集成 __Mybatis__ 
> * 使用自定义注解 __@TargetDataSource__ 实现了数据库读写分离。

##### 项目目标
* 该项目计划实现通过HTTP接口，动态管理基于Http和Thrift调用的Quartz任务(任务的 添加、查询、禁用、启用、触发)。
比如添加一个基于HTTP接口调用的定时任务，只需要向接口传递数据
```json
{
  "jobDO": {
    "description": "测试心跳检测",
    "group": "TEST_HTTP_JOB",
    "name": "test_heart_beat",
    "targetClass": "com.lvhao.schedulejob.schedule.HttpJob",
    "extInfo": {
      "type": "http_job",
      "method": "get",
      "url": "http://localhost:54321/heart_beat",
      "jsonParams": ""
    }
  },
  "triggerDOs": [
    {
      "cronExpression": "0/30 * * * * ?",
      "description": "心跳检测每30秒调用一次",
      "group": "TEST_HTTP_TRIGGER",
      "name": "test_heart_beat_trigger"
    }
  ]
}
```

