# schedule-job
项目采用Spring Boot框架，集成了分布式任务调度框架Quartz。使用SpringMVC作为路由控制，JSON 数据解析使用fastjson 替换了 jackson。HTTP接口调用使用OKhttp3 替代了 HttpClient。 Thrift调用实现了client池化管理。数据持久层集成Mybatis框架，并基于Spring AbstractRoutingDataSource类 结合自定义注解@TargetDataSource实现了数据库读写分离。

该项目计划实现通过HTTP接口，动态管理Quartz任务(任务的 添加、查询、禁用、启用、触发)。
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

