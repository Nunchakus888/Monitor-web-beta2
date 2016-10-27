package com.sumscope.cdhplus.web.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by wenshuai.li on 2016/10/14.
 */
public class ZookeeperNode {
    private String name;
    private String value;
    private String path;
    private Map<String,String> properties;

    private List<ZookeeperNode> children;

    private ResultObj resultObj;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ZookeeperNode> getChildren() {
        return children;
    }

    public void setChildren(List<ZookeeperNode> children) {
        this.children = children;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public ResultObj getResultObj() {
        return resultObj;
    }

    public void setResultObj(ResultObj resultObj) {
        this.resultObj = resultObj;
    }
}
