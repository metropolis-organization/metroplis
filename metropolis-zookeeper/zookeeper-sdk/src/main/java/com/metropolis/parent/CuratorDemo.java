package com.metropolis.parent;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author Pop
 * @date 2020/3/14 15:01
 */
public class CuratorDemo {


    public static void main(String[] args) throws Exception {

        /**
         * 创建一个连接
         * 集群写法，
         * 192.168.1.12:2181,192.168.1.14:2181,192.168.1.15:2181
         */
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.1.12:2181").sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        /**
         * 只重试一次， RetryOneTime
         * RetryOneTime
         * RetryUnitElapsed
         * RetryNTimes
         */
        curatorFramework.start();//启动

        //CRUD
//        createData(curatorFramework);
//        updateData(curatorFramework);
        deleteData(curatorFramework);
    }

    private static void createData(CuratorFramework curatorFramework) throws Exception {

        //创建节点   默认持久化节点,这里关创建临时节点，连接关闭就删除CreateMode.EPHEMERAL
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).
                //creatingParentsIfNeeded 由于zk中节点要一层层创建
                //有了这个api可以一同创建
//                creatingParentsIfNeeded().
                forPath("/data/program","test".getBytes());

    }

    private static void updateData(CuratorFramework curatorFramework) throws Exception{
        curatorFramework.setData().forPath("/data/program","update".getBytes());
    }

    private static void deleteData(CuratorFramework curatorFramework) throws Exception{
        /**
         * 由于zk是多个服务并发访问，所以有个版本号的功能，类似与乐观锁，如果与传入的版本号不符合，
         * 那么无法进行相应的操作
         *
         * 可以通过 stat /data/program 查看节点状态
         */
//        curatorFramework.delete().withVersion(0).forPath("/data/program");
        //org.apache.zookeeper.KeeperException$BadVersionException: KeeperErrorCode = BadVersion for /data/program
        //因为原本的dataVersion已经变成1，所以 0 会失败，改成1就可以了
        curatorFramework.delete().withVersion(1).forPath("/data/program");

        //也可以支持动态的获取节点信息
        Stat stat = new Stat();
        String value = new String(curatorFramework.getData().storingStatIn(stat).forPath("/data/program"));
        curatorFramework.delete().withVersion(stat.getVersion()).forPath("/data/program");
    }

}
