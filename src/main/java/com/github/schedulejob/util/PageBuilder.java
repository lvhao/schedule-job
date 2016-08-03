package com.github.schedulejob.util;

import com.github.schedulejob.common.Page;

/**
 * 构造page
 *
 * Created by root on 2016/6/25 0025.
 */
public class PageBuilder {
    public static final Page DEFAULT_PAGE_INFO = new Page(1, 10, 0);
    private Page page;

    public static PageBuilder newPage(){
        PageBuilder pb = new PageBuilder();
        pb.page = DEFAULT_PAGE_INFO;
        return pb;
    }

    public PageBuilder withIndex(int index){
        this.page.setIndex(index);
        return this;
    }

    public PageBuilder withSize(int size){
        this.page.setIndex(size);
        return this;
    }

    public Page build(){
        return this.page;
    }
}
