package com.github.schedulejob.common;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-6-24 18:52
 */
public class ResponseBuilder {
    private Response resp;

    public static ResponseBuilder newResponse(){
        ResponseBuilder instance = new ResponseBuilder();
        instance.resp = new Response();
        instance.resp.setData(new Object());
        return instance;
    }
    public ResponseBuilder withCode(String code){
        this.resp.setCode(code);
        return this;
    }
    public ResponseBuilder withMsg(String msg){
        this.resp.setMsg(msg);
        return this;
    }
    public ResponseBuilder withData(Object o){
        this.resp.setData(o);
        return this;
    }
    public Response build(){
        return this.resp;
    }
}
