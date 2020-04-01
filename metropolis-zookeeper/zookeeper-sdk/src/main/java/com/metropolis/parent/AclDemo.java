package com.metropolis.parent;

import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pop
 * @date 2020/3/14 15:55
 */
public class AclDemo {
    public static void main(String[] args) throws Exception {
        List<AuthInfo> authInfos = new ArrayList<>();
        AuthInfo authInfo = new AuthInfo("digest",DigestAuthenticationProvider.generateDigest("admin:admin").getBytes());
        authInfos.add(authInfo);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.1.12:2181").sessionTimeoutMs(5000)
                .authorization(authInfos)//对当前连接授权
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        curatorFramework.start();

        //设置权限
        List<ACL> list = new ArrayList<>();
        //账户 admin 密码 admin 对 /auth 节点有读写操作
        /**
         * id 权限模式
         * IP（根据ip段来访问）/Digest（根据账号密码来访问）/
         * world开放的/ super 超级用户
         */
        ACL acl = new ACL(ZooDefs.Perms.READ|ZooDefs.Perms.WRITE
                ,new Id("digest",DigestAuthenticationProvider.generateDigest("admin:admin")));
//        new Id("world","anyone");
//        new Id("ip","192.168.1.12");

        list.add(acl);
        curatorFramework.create().withMode(CreateMode.PERSISTENT).withACL(list).forPath("/auth");
        //对某个已经存在的节点设置权限
        curatorFramework.setACL().withACL(list).forPath("/temp");
        //如果你不想自己创建，也可以自己用ids来创建
//        ZooDefs.Ids.ANYONE_ID_UNSAFE;
    }

}
