package com.lvhao.schedulejob.domain.job;

import com.lvhao.schedulejob.common.AppConst;

/**
 * Http任务扩展字段
 *
 * @author: lvhao
 * @since: 2016-7-22 14:47
 */
public class HttpJobDO extends JobDO{

    // ext info
    private String type = AppConst.JobType.HTTP_JOB;
    private String method;
    private String url;
    private String jsonParams;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsonParams() {
        return jsonParams;
    }

    public void setJsonParams(String jsonParams) {
        this.jsonParams = jsonParams;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HttpJobDO{");
        sb.append("jsonParams='").append(jsonParams).append('\'');
        sb.append(", method='").append(method).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
