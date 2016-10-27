package com.sumscope.cdhplus.web.domain;

/**
 * Created by Roidder on 2016/10/10.
 */
public class ApiUsageTakeTime {
    private String apiName;
    private int sqlTakeTime;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public int getTakeTime() {
        return sqlTakeTime;
    }

    public void setTakeTime(int sqlTakeTime) {
        this.sqlTakeTime = sqlTakeTime;
    }
}
