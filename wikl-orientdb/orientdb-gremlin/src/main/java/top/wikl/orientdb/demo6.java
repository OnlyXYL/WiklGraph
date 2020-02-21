package top.wikl.orientdb;

import com.orientechnologies.orient.client.remote.OStorageRemote;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

/**
 * 压力测试方式验证负载均衡--------工厂方式
 * <p>
 * <p>
 * 119.18.198.137:10600>> 10.0.43.101:2424
 * <p>
 * <p>
 * 119.18.198.137:10601>> 10.0.43.104:2424
 *
 * @author XYL
 * @title: demo6
 * @description: TODO
 * @date 2020/2/18 14:15
 * @return
 * @since V1.0
 */
public class demo6 {

    public static void main(String[] args) {

        String url = "remote:119.18.198.137:10600/xyl;remote:119.18.198.137:10601/xyl";

        //1. 获取工厂
        OrientGraphFactory factory = new OrientGraphFactory(url);

        factory.setConnectionStrategy(OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_REQUEST.toString());

        //线程数
        int threadNum = 1000;

        new Thread(() -> {
            //调用方法
            multiThread(factory, threadNum);
        }).run();

    }

    /**
     * 多线程查询
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/2/18 16:17
     * @since V1.0
     */
    private static void multiThread(OrientGraphFactory factory, int threadNum) {

        for (int i = 0; i < threadNum; i++) {

            new Thread(() -> {

                OrientGraphNoTx graph = factory.getNoTx();

                String connectionStrategy = graph.getConnectionStrategy();

                System.out.println("连接策略：" + connectionStrategy);

                Iterable<Vertex> person = graph.getVerticesOfClass("person");

                person.forEach(x ->
                        System.out.println(x.getId().toString())
                );
            }).run();

            System.out.println(" ");
            System.out.println("----------------");
            System.out.println(" ");
        }
    }

}
