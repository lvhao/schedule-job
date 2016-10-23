# schedule-job [![License](https://img.shields.io/github/license/mashape/apistatus.svg)](http://www.opensource.org/licenses/mit-license.php) [![Build Status](https://travis-ci.org/mybatis/spring.svg?branch=master)](https://travis-ci.org/mybatis/spring) [![unstable](http://badges.github.io/stability-badges/dist/unstable.svg)](http://github.com/badges/stability-badges)
#### 项目基于 __jdk1.8__ 采用 __Spring Boot__ 框架
> * 集成了分布式任务调度框架 __Quartz__ ，任务存储于数据库。
> * 使用SpringMVC作为路由控制, 集成 __Swagger2__ 提供实时 __Restful__ API文档。
> * 数据持久层集成 __Mybatis__ 框架。
> * 使用自定义注解 __@TargetDataSource__ 实现了多数据源动态切换，支持数据库读写分离。
> * __HTTP JOB__ 接口调用使用 __OkHttp3__ 替代了 __HttpClient__ 。
> * __Thrift JOB__ 接口调用实现了 __Thrift client__ 池化管理。
> * 集成了 __Spring data redis__，提供缓存服务。

##### 项目目标
* 该项目计划实现通过Restful接口，动态管理基于Http(已完成)和Thrift调用的Quartz任务(任务的 添加、查询、禁用、启用、触发)。
比如添加一个基于HTTP接口调用的定时任务，只需要向接口传递JSON数据。

#### 常见问题

#### 
1. 查询任务列表接口
```shell
curl -X GET -H "Cache-Control: no-cache" "http://localhost:54321/jobs"
```

2. 添加任务接口
```shell
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
  "jobDO": {
    "description": "测试心跳检测",
    "group": "TEST_HTTP_JOB",
    "name": "test_heart_beat",
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
}' "http://localhost:54321/jobs"
```

3. 查询任务接口
```shell
curl -X GET -H "Cache-Control: no-cache" "http://localhost:54321/jobs/{jobKey}/"
```

4. 移除任务接口
```shell
curl -X DELETE -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
    "TEST_HTTP_JOB" : ["sync_test_job"]
}' "http://localhost:54321/jobs"
```

5. 触发任务接口
```shell
curl -X POST -H "Content-Type: application/json" 
             -H "Cache-Control: no-cache" -d '' "http://localhost:54321/jobs/{groupName}/{taskName}"
```

