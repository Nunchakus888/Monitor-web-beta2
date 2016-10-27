package com.sumscope.cdhplus.zookeeper;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;


public class ZookeeperInstance {

    private ZooKeeper zooKeeper;
    private Object waiter = new Object();

    private static final int SESSION_TIME_OUT = 1000* 200;

    private String connectString;

    public ZookeeperInstance(String connectString){
        this.connectString = connectString;
    }
    /**
     * 连接zookeeper
     *
     * @throws IOException
     */
    public void connect() throws IOException {
        synchronized (waiter) {
            try {
                SessionWatcher watcher = new SessionWatcher();
                // session的构建是异步的
                this.zooKeeper = new ZooKeeper(connectString, SESSION_TIME_OUT, watcher, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            waiter.notifyAll();
        }
    }

    /**
     * 在指定路径创建znode，并初始化数据
     * @param path　znode节点路径
     * @param data　数据
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createNode(String path, byte[] data) throws KeeperException, InterruptedException {
        return this.zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 获取指定路径所有孩子节点
     * @param path　znode节点路径
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws InterruptedException {
        try{
            return this.zooKeeper.getChildren(path, false);
        }catch (KeeperException e){
            return null;
        }
    }

    /**
     * 在指定路径设置数据
     * @param path　znode节点路径
     * @param data　重置数据
     * @param version　节点版本
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat setData(String path, byte [] data, int version) throws KeeperException, InterruptedException {
        return this.zooKeeper.setData(path, data, version);
    }

    /**
     * 根据路径获取节点数据
     *
     * @param path　znode节点路径
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return this.zooKeeper.getData(path, false, null);
    }

    public String getStringData(String path) throws KeeperException, InterruptedException {
        return new String(getData(path));
    }
    /**
     * 判断某个节点是否存在
     * @param path　znode节点路径
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean exists(String path) throws KeeperException, InterruptedException {
        return null!=this.zooKeeper.exists(path,false);
    }

    /**
     * 删除节点
     *
     * @param path　znode节点路径
     * @param version　节点版本
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void deleteNode(String path, int version) throws InterruptedException, KeeperException {
        List<String> children = zooKeeper.getChildren(path,false);
        for(String child : children){
            String childPath = path + "/" + child;
            deleteNode(childPath,version);
        }
        try{
            zooKeeper.delete(path,-1);
        }catch(KeeperException.NoNodeException e){
            //ignore
        }
    }

    /**
     * 关闭zookeeper连接
     * @throws InterruptedException
     */
    public void closeConnect() throws InterruptedException {
        try {
            synchronized (waiter) {
                if (this.zooKeeper != null) {
                    zooKeeper.close();
                }
                waiter.notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class SessionWatcher implements Watcher {

        public void process(WatchedEvent event) {
            // 如果是“数据变更”事件
            if (event.getType() != Event.EventType.None) {
                return;
            }
            // 如果是链接状态迁移
            // 参见keeperState
            synchronized (waiter) {
                switch (event.getState()) {
                    // zk连接建立成功,或者重连成功
                    case SyncConnected:
                        System.out.println("Connected...");
                        waiter.notifyAll();
                        break;
                    // session过期,这是个非常严重的问题,有可能client端出现了问题,也有可能zk环境故障
                    // 此处仅仅是重新实例化zk client
                    case Expired:
                        System.out.println("Expired...");
                        // 重连
                        try {
                            connect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    // session过期
                    case Disconnected:
                        // 链接断开，或session迁移
                        System.out.println("Connecting....");
                        break;
                    case AuthFailed:
                        try {
                            closeConnect();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        throw new RuntimeException("ZK Connection auth failed...");
                    default:
                        break;
                }
            }
        }
    }
}

