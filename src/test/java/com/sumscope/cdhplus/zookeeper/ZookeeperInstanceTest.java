
package com.sumscope.cdhplus.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


/**
 * Created by wenshuai.li on 2016/10/13.
 */


public class ZookeeperInstanceTest {
    ZookeeperInstance zookeeperInstance;

    String root = "/com/sumscope/cdhplus";

    /*@Before
    public void init() throws IOException {
        String host = "172.16.87.2:2181";
        zookeeperInstance = new ZookeeperInstance(host);

        zookeeperInstance.connect();
    }

    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        List<String> list = zookeeperInstance.getChildren("/com/sumscope/java");
        System.out.println(list);

        zookeeperInstance.closeConnect();
    }

    @Test
    public void createChildren() throws KeeperException, InterruptedException {

        try{
            byte [] data = "12133".getBytes();
            String result = zookeeperInstance.createNode("/com/sumscope/java/j2ee", data);
            System.out.println(result);
            System.out.println("4、--------create znode ok-----------\n");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            zookeeperInstance.closeConnect();
        }
    }

    @Test
    public void deleteNode() throws KeeperException, InterruptedException {


        try{
            zookeeperInstance.deleteNode("/com/sumscope/cdhplus",0);
        }finally {
            zookeeperInstance.closeConnect();
        }
    }


    @Test
    public void getData() throws KeeperException, InterruptedException {
        try{
            byte[] data =  zookeeperInstance.getData("/com/sumscope1");
            System.out.println(new String(data));
        }finally {
            zookeeperInstance.closeConnect();
        }
    }

    @Test
    public void setData() throws KeeperException, InterruptedException {
        try{
            byte[] data =  zookeeperInstance.getData("/com/sumscope");
            System.out.println(new String(data));
        }finally {
            zookeeperInstance.closeConnect();
        }
    }*/
}
