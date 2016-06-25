package com.github.schedulejob.common;

/**
 * 返回码常量
 *
 * Created by root on 2016/6/25 0025.
 */
public class RetCodeConst {
    public static final RetCode OK = RetCode.of("0000","成功");
    public static final RetCode ERROR = RetCode.of("1001","错误");
    public static final RetCode PARAM_ERR = RetCode.of("1002","参数错误");

}
