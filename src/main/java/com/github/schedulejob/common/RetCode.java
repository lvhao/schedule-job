package com.github.schedulejob.common;

/**
 * 返回码定义
 *
 * Created by root on 2016/6/25 0025.
 */
public final class RetCode {
    private String code;
    private String msg;

    private RetCode(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static RetCode of(String code, String msg){
        return new RetCode(code,msg);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RetCode{");
        sb.append("code='").append(code).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
