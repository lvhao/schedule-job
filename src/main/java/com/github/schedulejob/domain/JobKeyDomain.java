package com.github.schedulejob.domain;

/**
 * Created by root on 2016/7/3 0003.
 */
public class JobKeyDomain {
    private String name;
    private String group;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobKeyDomain{");
        sb.append("name='").append(name).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
