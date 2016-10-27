package com.sumscope.cdhplus.web.service;

import com.sumscope.cdhplus.web.domain.ZookeeperNode;
import com.sumscope.cdhplus.zookeeper.Visualable;
import com.sumscope.cdhplus.zookeeper.VisualableImpl;
import com.sumscope.cdhplus.zookeeper.ZookeeperInstance;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by wenshuai.li on 2016/10/14.
 */
@Component
@Configuration
public class ZookeeperService {
    private ZookeeperInstance zookeeperInstance;

    private String root = "/com/sumscope";
    private int version = -1;

    private Visualable visualable;
    @Value("${spring.zookeeper.connect}")
    private String connect;

    @PostConstruct
    public void init() throws IOException{
        zookeeperInstance = new ZookeeperInstance(connect);
        zookeeperInstance.connect();

        visualable = new VisualableImpl(zookeeperInstance);
    }
    public ZookeeperNode getZookeeperNodes(String path,int depth) throws InterruptedException {
        ZookeeperNode ZookeeperNode = new ZookeeperNode();
        try{
            String value = zookeeperInstance.getStringData(path);
            ZookeeperNode.setValue(value);
            ZookeeperNode.setPath(path);
            ZookeeperNode.setName(path);

            visualable.visual(ZookeeperNode,depth);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ZookeeperNode;
    }

    public void createNode(String path,String data) throws InterruptedException {
        try{
            zookeeperInstance.createNode(path,data.getBytes());
        }catch (KeeperException e){
            e.printStackTrace();
        }

    }

    public void delNode(String path) throws InterruptedException {
        try {
            zookeeperInstance.deleteNode(path,version);
        }catch (KeeperException e){
            e.printStackTrace();
        }

    }

    public boolean updateData(String path,String data) throws InterruptedException {
        try{
            if(zookeeperInstance.exists(path)){
                zookeeperInstance.setData(path,data.getBytes(),version);
                return true;
            }
        }catch (KeeperException e){
            e.printStackTrace();
        }
        return false;
    }
}
