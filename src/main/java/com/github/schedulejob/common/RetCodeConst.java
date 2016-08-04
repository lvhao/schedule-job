package com.github.schedulejob.common;

/**
 * 返回码常量
 *
 * Created by root on 2016/6/25 0025.
 */
public class RetCodeConst {

    // 通用码
    public static final RetCode UN_KNOWN = RetCode.of("-1","未知");
    public static final RetCode OK = RetCode.of("0000","成功");
    public static final RetCode ERROR = RetCode.of("1001","错误");
    public static final RetCode RESOURCE_NON_EXISTE = RetCode.of("1002","资源不存在");
    public static final RetCode ACCESS_DENY = RetCode.of("1003","访问拒绝");
    public static final RetCode AUTH_FAIL = RetCode.of("1004","鉴权失败");

    // 客户端错误码 4000
    public static final RetCode CLIENT_ERROR = RetCode.of("4000","客户端错误");
    public static final RetCode CLIENT_PARAM_ERROR = RetCode.of("4001","参数错误");
    public static final RetCode CLIENT_AUTH_ERROR = RetCode.of("4002","用户鉴权错误");
    public static final RetCode CLIENT_FORBIDDEN_ERROR = RetCode.of("4003","用户无权限");

    // 服务端错误码 5000
    public static final RetCode SERVER_ERROR = RetCode.of("5000","服务端错误");

    // RPC端错误码 6000
    public static final RetCode RPC_ERROR = RetCode.of("6000","RPC调用错误");
    public static final RetCode RPC_TIMEOUT_ERROR = RetCode.of("6001","RPC调用超时");
}
