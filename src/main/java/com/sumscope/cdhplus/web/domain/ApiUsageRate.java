package com.sumscope.cdhplus.web.domain;

/**
 * Created by Roidder on 2016/10/10.
 */
public class ApiUsageRate {
    private String apiName;
    private int countApi;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public int getCountApi() {
        return countApi;
    }

    public void setCountApi(int countApi) {
        this.countApi = countApi;
    }
}
