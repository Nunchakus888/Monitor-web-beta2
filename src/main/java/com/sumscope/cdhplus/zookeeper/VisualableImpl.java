package com.sumscope.cdhplus.zookeeper;

import com.sumscope.cdhplus.web.domain.ZookeeperNode;
import org.apache.zookeeper.KeeperException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenshuai.li on 2016/10/14.
 */
public class VisualableImpl implements Visualable {

    private ZookeeperInstance zookeeperInstance;
    public VisualableImpl(ZookeeperInstance zookeeperInstance){
        this.zookeeperInstance = zookeeperInstance;
    }

    @Override
    public void visual(ZookeeperNode  zookeeperNode,int depth) throws KeeperException, InterruptedException {
        depth = 1;
        if(depth == 0){
            return ;
        }
        List<String> list = zookeeperInstance.getChildren(zookeeperNode.getPath());
        List<ZookeeperNode> zookeeperNodeList = new ArrayList<>();
        for (String node : list){
            String fullPath = zookeeperNode.getPath() + "/" + node;
            String value =  zookeeperInstance.getStringData(fullPath);

            ZookeeperNode zd = new ZookeeperNode();
            zd.setName(node);
            zd.setPath(fullPath);
            zd.setValue(value);
            zd.setChildren(new ArrayList<>());

            zookeeperNodeList.add(zd);
        }
        zookeeperNode.setChildren(zookeeperNodeList);
        depth--;
    }
}
