package com.github.schedulejob.common;

/**
 * 分页参数类
 * Created by root on 2016/6/25 0025.
 */
public class Page {
    private int index;
    private int size;
    private int totalCount;

    public Page(){}
    public Page(int index,int size,int totalCount){
        this.index = index;
        this.size = size;
        this.totalCount = totalCount;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Page{");
        sb.append("index=").append(index);
        sb.append(", size=").append(size);
        sb.append(", totalCount=").append(totalCount);
        sb.append('}');
        return sb.toString();
    }
}
