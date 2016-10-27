
package com.sumscope.cdhplus.web.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by wenshuai.li on 2015/10/28.
 */

public class TaskExecutor  {
    public static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(5));


}

