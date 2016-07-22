package com.lvhao.schedulejob.domain.job;

import com.lvhao.schedulejob.common.AppConst;

/**
 * thrift任务扩展字段
 *
 * @author: lvhao
 * @since: 2016-7-22 14:48
 */
public class ThriftJobDO extends JobDO {

    // ext info
    private String type = AppConst.JobType.THRIFT_JOB;
    private String thriftIface;
    private String method;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThriftIface() {
        return thriftIface;
    }

    public void setThriftIface(String thriftIface) {
        this.thriftIface = thriftIface;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ThriftJobDO{");
        sb.append("method='").append(method).append('\'');
        sb.append(", thriftIface='").append(thriftIface).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
