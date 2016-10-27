package com.sumscope.cdhplus.web; /**
 * Created by Administrator on 2015/10/23.
 */

import com.sumscope.cdhplus.web.service.ApiUsageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = ServletInitializer.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class ServletInitializerTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private ApiUsageService apiUsageService;

    @Test
    public void queryApiUsageDetails() throws Exception {
        Map map = new HashMap();
        map.put("apiName", "Bond");
        int size = 15,
            start = 0 * size;
        map.put("page", start);
        map.put("size", size);
        apiUsageService.queryApiUsageDetails(map);
    }

    @Test
    public void queryApiUsageRate() throws Exception {
                apiUsageService.queryApiUsageRate();
    }

    @Test
    public void queryApiUsageTakeTime() throws Exception {
        apiUsageService.queryApiUsageTakeTime();

    }

    @Test
    public void queryApiUsageByUsersRate() throws Exception {
        apiUsageService.queryApiUsageByUsersRate();
    }

    @Test
    public void queryErrorDetails() throws Exception {
        int size = 15;
        int start = 0;
        Map map = new HashMap();
        map.put("page", start);
        map.put("size", size);
        map.put("userName", "");
        map.put("method", "");
        map.put("className", "");
        map.put("dataSource", "0");
        apiUsageService.queryErrorDetails(map);
    }

    @Test
    public void queryUserUsageApiList() throws Exception {
        int size = 20;
        int start = 0;
        Map map = new HashMap();
        map.put("page", start);
        map.put("size", size);
        map.put("username", "liuyan");
        apiUsageService.queryUserUsageApiList(map);
    }

    @Test
    public void createTable() throws Exception {
        apiUsageService.createTable("admin_user_info");
    }

    @Test
    public void tableBackup() throws Exception {
        apiUsageService.tableBackup("t_log_backup");
    }

    @Test
    public void insertDatabaseT1IntoT2() throws Exception {
        apiUsageService.insertDatabaseT1IntoT2("t_log_backup");
    }
}