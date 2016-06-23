package com.github.schedulejob.domain;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-6-23 20:59
 */
public class TriggerDomain {

    // trigger info
    private String name;
    private String groupName;
    private String cronExpression;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TriggerDomain{" +
                "cronExpression='" + cronExpression + '\'' +
                ", description='" + description + '\'' +
                ", groupName='" + groupName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
