package com.sumscope.cdhplus.zookeeper;

import com.sumscope.cdhplus.web.domain.ZookeeperNode;
import org.apache.zookeeper.KeeperException;

/**
 * Created by wenshuai.li on 2016/10/14.
 */
public interface Visualable {
    void visual(ZookeeperNode  zookeeperNode,int depth) throws KeeperException, InterruptedException;

}
