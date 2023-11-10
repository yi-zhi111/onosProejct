package com.paul.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pxj
 * @date 2022-11-28 15:13
 */
@SpringBootTest
public class ZookeeperTest {
    @Test
    /**
     * Zookeeper连接
     */
    public void contestLoads23(){
        /**
         * @param connectString         连接字符串，zkServer地址和端口  serverIP:2181
         * @param sessionTimeoutMs      会话超时时间 单位ms
         * @param connectionTimeoutMs   连接超时时间 单位ms
         * @param retryPolicy           重试策略
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,10);
        //第一种方式
        //CuratorFramework client = CuratorFrameworkFactory.newClient("172.20.249.206:2181", 60 * 1000, 15 * 1000, retryPolicy);
        //开启连接

        //第二种方式
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("172.20.249.206:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy).namespace("paul").build();//名称空间
        client.start();


        /**
         * 创建节点：create 持久 临时 顺序 数据
         * 1.基本创建
         * 2.创建节点，带有数据
         * 3.设置节点的类型
         * 4.创建多及节点 /app1/p1
         */
        try {
            /**
             * 1.基本创建
             * 如果创建节点，没有指定数据，则默认将当前客户端的ip作为数据存储
             */
            //String s = client.create().forPath("/onos");
            //2.创建节点，带有数据
            //String s = client.create().forPath("/onos","haha".getBytes());
            //3.设置节点类型，节点默认类型是持久化
            //String s = client.create().withMode(CreateMode.EPHEMERAL).forPath("/onos");
            //4.创建多级节点:creatingParentsIfNeeded()如果父节点不存在，则创建父节点 /app1/p1
            //String s = client.create().creatingParentsIfNeeded().forPath("/app1/p1");
            //System.out.println(s);

            /**
             * 查询节点
             * 1.查询数据：get
             * 2.查询子节点：ls
             * 3.查询节点状态信息：ls -s
             */
            //1.查询数据：get
            //byte[] data = client.getData().forPath("/onos");
            //System.out.println(new String(data));
            //2.查询子节点：ls
            //List<String> strings = client.getChildren().forPath("/onos");
            //System.out.println(strings);
            //3.查询节点状态信息
            /*Stat status = new Stat();
            System.out.println(status);
            client.getData().storingStatIn(status).forPath("/onos");
            System.out.println(status);*/

            /**
             * 修改节点
             * 1.修改数据
             * 2.根据版本修改
             * version是status中的数据，为了让其它客户端或者线程不干扰
             */
            //1.修改数据
            //client.setData().forPath("/onos","haha".getBytes());
            //2.根据版本修改
            /*Stat status = new Stat();
            client.getData().storingStatIn(status).forPath("/onos");
            int version = status.getVersion();
            client.setData().withVersion(version).forPath("/onos","haha".getBytes());*/

            /**
             * 删除节点
             * 1.删除单个节点
             * 2.删除带有子节点的节点
             * 3.必须成功的删除
             * 4.回调
             */
            //1.删除单个节点
            //client.delete().forPath("/onos");
            //2.删除带有子节点的节点
            //client.delete().deletingChildrenIfNeeded().forPath("/onos");
            //3.必须成功的删除
            //client.delete().guaranteed().forPath("/onos");
            //4.回调
            client.delete().guaranteed().inBackground(new BackgroundCallback() {
                @Override
                public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                    System.out.println("deleted");
                    System.out.println(curatorEvent);
                }
            }).forPath("/onos");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (client != null) {
            client.close();
        }


    }

    /**
     * Zookeeper监听器
     */
    @Test
    public void contestLoads24(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,10);
        //第一种方式
        //CuratorFramework client = CuratorFrameworkFactory.newClient("172.20.249.206:2181", 60 * 1000, 15 * 1000, retryPolicy);
        //开启连接

        //第二种方式
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("172.20.249.206:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy).namespace("paul").build();//名称空间
        client.start();



        /**
         * 演示NodeCache：给指定的一个节点注册监听器
         */
        //1.创建NodeCache对象
        NodeCache nodeCache = new NodeCache(client,"/app1");
        //2.注册监听事件
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {

            }
        });
        nodeCache.getListenable().addListener(() -> {
            System.out.println("节点变化了");
            //获取修改节点后的数据
            byte[] data = nodeCache.getCurrentData().getData();
            System.out.println(new String(data));
        });
        //3.开启监听：如果设置为true，则开启监听时，加载缓冲数据
        /**
         * 演示PathChildrenCache：监听某个节点的所有子节点们
         */
        //1.创建监听对象
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client,"/app1",true) ;
        //2.绑定监听器
        pathChildrenCache.getListenable().addListener((curatorFramework,childrenCacheEvent) ->{
            System.out.println("子节点变化了");
            System.out.println(childrenCacheEvent);
            //监听子节点的数据变更，并且拿到变更后的数据
            //1.获取类型
            PathChildrenCacheEvent.Type type = childrenCacheEvent.getType();
            //2.判断类型是否是update
            if (type.equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                System.out.println("数据变化了");
                byte[] data = childrenCacheEvent.getData().getData();
                System.out.println(new String(data));
            }
        });

        /**
         *
         * TreeCache:监听某个节点自己和所有子节点
         */
        //1.创建监听器
        TreeCache treeCache = new TreeCache(client,"/app1");

        //2.注册监听
        treeCache.getListenable().addListener((curatorFramework,treeCacheEvent)->{
            System.out.println("节点变化了");
            System.out.println(treeCacheEvent);
        });
        //3.开启
        try {
            nodeCache.start(true);
            pathChildrenCache.start(true);
            treeCache.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        while (true) {

        }

    }
}
