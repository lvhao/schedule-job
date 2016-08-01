package com.github.schedulejob.common;

/**
 * 返回码
 *
 * @author: lvhao
 * @since: 2016-4-25 14:06
 */
public class Response<T> {

    private T data;
    private RetCode retCode;
    private Page page;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RetCode getRetCode() {
        return retCode;
    }

    public void setRetCode(RetCode retCode) {
        this.retCode = retCode;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Response{");
        sb.append("data=").append(data);
        sb.append(", retCode=").append(retCode);
        sb.append(", page=").append(page);
        sb.append('}');
        return sb.toString();
    }
}
