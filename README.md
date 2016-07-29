# schedule-job
项目采用springboot框架，集成了分布式任务调度框架 quartz, 数据持久层采用Mybatis框架，并基于自定义注解实现了读写分离。

该项目计划实现通过HTTP接口，动态管理Quartz任务。
比如添加一个基于HTTP接口调用的定时任务，只需要向接口传递数据
```json
{
  "jobDO": {
    "description": "测试心跳检测",
    "group": "TEST_HTTP_JOB",
    "name": "test_heart_beat",
    "targetClass": "com.lvhao.schedulejob.schedule.HttpJob",
    "extInfo": {
      "type": "http_job", // http_job,thrift_job
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
