package com.metropolis.parent;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Pop
 * @date 2020/3/14 16:24
 */
public class WatcherDemo {

    public static void main(String[] args) throws Exception {
        /**
         * PathChildCache 子节点curd触发
         * NodeCache 当前节点crud触发
         * TreeCache 上面两种综合体
         */
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.1.12:2181").sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        curatorFramework.start();
        addListenerWithNode(curatorFramework);
        System.in.read();
    }

    private static void addListenerWithNode(CuratorFramework curatorFramework) throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework,"/watcher",false);
        NodeCacheListener nodeCacheListener = ()->{
            System.out.println("接受到 node 变化"+nodeCache.getCurrentData().getData()+"/");

        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        //当前节点的监听
        nodeCache.start();
    }

    private static void addListenerWithChildNode(CuratorFramework curatorFramework) throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework,"/watcher",false);
       //自动重复监听
        PathChildrenCacheListener nodeCacheListener= (curatorFramework1,pathChildrenCacheListener)->{
            // 事件类型
            curatorFramework1.getChildren();//触发后，可以再次获得子节点信息
            System.out.println(pathChildrenCacheListener.getType()+"->"
                    +new String(pathChildrenCacheListener.getData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        //当前节点的监听
        nodeCache.start(PathChildrenCache.StartMode.NORMAL);
    }

}
