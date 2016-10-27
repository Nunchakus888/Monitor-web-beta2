package com.sumscope.cdhplus.web.domain;

/**
 * Created by Roidder on 2016/10/10.
 */
public class ApiUsageByUsersRate {
    private String userName;
    private int apiUsageByUserRate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getApiUsageRage() {
        return apiUsageByUserRate;
    }

    public void setApiUsageRage(int apiUsageRage) {
        this.apiUsageByUserRate = apiUsageRage;
    }
}
