package com.sumscope.cdhplus.web.service;

import com.sumscope.cdhplus.web.domain.*;
import com.sumscope.cdhplus.web.mapper.ApiUsageMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by Roidder on 2016/10/10.
 */
public class ApiUsageService {

    @Autowired
    private ApiUsageMapper apiUsageMapper;

    public List<ApiUsageRate> queryApiUsageRate(){
        List<ApiUsageRate> list = apiUsageMapper.queryApiUsageRate();
        return list;
    }

    public List<ApiUsageTakeTime> queryApiUsageTakeTime(){
        List<ApiUsageTakeTime> list = apiUsageMapper.queryApiUsageTakeTime();
        return list;
    }

    public List<ApiUsageDetails> queryApiUsageDetails(Map map){
        List<ApiUsageDetails> list = apiUsageMapper.queryApiUsageDetails(map);
        return list;
    }

    public List<ApiUsageByUsersRate> queryApiUsageByUsersRate(){
        List<ApiUsageByUsersRate> list = apiUsageMapper.queryApiUsageByUsersRate();
        return list;
    }

    public List<UserUsageApiList> queryUserUsageApiList(Map map){
        List<UserUsageApiList> lists = apiUsageMapper.queryUserUsageApiList(map);
        return lists;
    }

    public List<ErrorDetails> queryErrorDetails(Map map){
        List<ErrorDetails> lists = apiUsageMapper.queryErrorDetails(map);
        return lists;
    }

    public int createTable(String tableName){
        int result = apiUsageMapper.createTable(tableName);
        return result;
    }

    //backup table
    public int tableBackup(String tableName){
        int result = apiUsageMapper.tableBackup(tableName);
        return result;
    }

    //insert table's data to backup_table.
    public int insertDatabaseT1IntoT2(String intoTableName){
        int result = apiUsageMapper.insertData(intoTableName);
        return result;
    }

    public ApiUsageMapper getApiUsageMapper() {
        return apiUsageMapper;
    }

    public void setApiUsageMapper(ApiUsageMapper apiUsageMapper) {
        this.apiUsageMapper = apiUsageMapper;
    }
}
