package com.sumscope.cdhplus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by wenshuai.li on 2016/4/6.
 */
//@RestController
//@RequestMapping(value = "/index", produces = "application/json; charset=UTF-8")
public final class IndexController {

    @RequestMapping(value = "/index")
    public String index(Map<String, Object> model) {
        return "index";
    }

    @RequestMapping(value = "/bond/v")
    public String welcome(Map<String, Object> model) {
        return "bestBidOffer";
    }

    @RequestMapping(value = "/bond/tradeToday")
    public String tradeToday(Map<String, Object> model) {
        return "tradeToday";
    }
}