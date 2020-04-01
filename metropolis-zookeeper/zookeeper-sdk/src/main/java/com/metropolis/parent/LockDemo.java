package com.metropolis.parent;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author Pop
 * @date 2020/3/14 18:29
 */
public class LockDemo {

    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.1.12:2181").sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        curatorFramework.start();
        //分布式锁  临时有序节点
        final InterProcessMutex lock = new InterProcessMutex(curatorFramework,"/lock");

        //魔方竞争
        for (int i = 0 ;i<10;i++){

            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" 尝试争抢锁");

                try {
                    lock.acquire();//阻塞
                    System.out.println(Thread.currentThread().getName()+" 已经获得锁");
                    Thread.sleep(400);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        lock.release();//释放锁
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

}
