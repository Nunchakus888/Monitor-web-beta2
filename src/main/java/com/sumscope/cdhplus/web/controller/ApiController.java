package com.sumscope.cdhplus.web.controller;

import com.sumscope.cdhplus.web.domain.*;
import com.sumscope.cdhplus.web.service.ApiUsageService;
import com.sumscope.cdhplus.web.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by wenshuai.li on 2016/4/6.
 */

@RestController
@RequestMapping(value = "/bond", produces = "application/json; charset=UTF-8")
public final class ApiController {
    @Autowired
    private ApiUsageService apiUsageService;

    public void setResponseHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.addHeader("Access-Control-Max-Age", "60");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Accept-Charset", "UTF-8");
    }

    @RequestMapping(value = "/apiUsageRate")
    public String apiUsageRate(HttpServletRequest request, HttpServletResponse response) {
        this.setResponseHeader(request, response);
        List<ApiUsageRate> list = apiUsageService.queryApiUsageRate();
        return JsonUtil.writeValueAsString(list);
    }

    @RequestMapping(value = "/sqlTaketime")
    public String apiUsageTakeTime(HttpServletRequest request, HttpServletResponse response) {
        this.setResponseHeader(request, response);
        List<ApiUsageTakeTime> list = apiUsageService.queryApiUsageTakeTime();
        return JsonUtil.writeValueAsString(list);
    }

    @RequestMapping(value = "/apiUsagedetails", method = RequestMethod.POST)
    public String apiDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody String query) {
        this.setResponseHeader(request, response);
        Map map = JsonUtil.json2Map(query);
        int size = 20;
        int start = (Integer.parseInt(map.get("page").toString()) - 1) * size;
        map.put("page", start);
        map.put("size", size);
        List<ApiUsageDetails> list = apiUsageService.queryApiUsageDetails(map);
        return JsonUtil.writeValueAsString(list);
    }

    @RequestMapping(value = "/userUsageApiRate")
    public String apiUsageByUsersRate(HttpServletRequest request, HttpServletResponse response) {
        this.setResponseHeader(request, response);
        List<ApiUsageByUsersRate> list = apiUsageService.queryApiUsageByUsersRate();
        return JsonUtil.writeValueAsString(list);
    }

    @RequestMapping(value = "/aUserUsageApiList", method = RequestMethod.POST)
    public String aUserUsageApiList(HttpServletRequest request, HttpServletResponse response,@RequestBody String query) {
        this.setResponseHeader(request, response);
        Map map = JsonUtil.json2Map(query);
        int size = 10;
        int start = (Integer.parseInt(map.get("page").toString()) - 1) * size;
        map.put("page", start);
        map.put("size", size);
        List<UserUsageApiList> list = apiUsageService.queryUserUsageApiList(map);
        return JsonUtil.writeValueAsString(list);
    }

    @RequestMapping(value = "/error", method = RequestMethod.POST)
    public String errorDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody String query) {
        this.setResponseHeader(request, response);
        Map map = JsonUtil.json2Map(query);
        int size = 20;
        int start = (Integer.parseInt(map.get("page").toString()) - 1) * size;
        map.put("page", start);
        map.put("size", size);
        List<ErrorDetails> list = apiUsageService.queryErrorDetails(map);
        return JsonUtil.writeValueAsString(list);
    }
}