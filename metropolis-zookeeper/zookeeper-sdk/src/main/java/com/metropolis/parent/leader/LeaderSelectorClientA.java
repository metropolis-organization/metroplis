package com.metropolis.parent.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Pop
 * @date 2020/3/14 21:30
 *
 * LeaderSelector方式进行leader选举
 */
public class LeaderSelectorClientA extends LeaderSelectorListenerAdapter implements Closeable {

   private String name;
   private LeaderSelector leaderSelector;
   private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void setLeaderSelector(LeaderSelector leaderSelector) {
        this.leaderSelector = leaderSelector;
    }

    public LeaderSelectorClientA(String name) throws InterruptedException {
        this.name = name;


    }

    public void start() throws IOException {
        leaderSelector.autoRequeue();//如果因为网络通信断开导致
        //失去节点，将会重新加入子节点进行选举。


        leaderSelector.start();
        System.in.read();
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        //其实是分布式锁的一种实现，获得锁的线程意味着成为了Leader执行这段逻辑
        //这方法执行玩，锁将会释放，不会再是leader
        System.out.println(name+" become leader");


        countDownLatch.await();//阻塞，不希望放弃Leader
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.1.12:2181").sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        curatorFramework.start();

        LeaderSelectorClientA clientA = new LeaderSelectorClientA("clientA");
        //发出选举的对象，如果竞选成功，将会执行clientA中定义的方法
        LeaderSelector leaderSelector = new LeaderSelector(curatorFramework,"/leader",clientA);
        clientA.setLeaderSelector(leaderSelector);
        clientA.start();
    }
}
