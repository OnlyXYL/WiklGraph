package top.wikl.orientdb;

import com.orientechnologies.orient.client.remote.OStorageRemote;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.*;

/**
 * 常规方式验证负载均衡
 * <p>
 * 存在问题：
 * orientdb-jdbc 2.2.37版本的jar包中含有 orietndb-graphdb 依赖，而官方给的工厂方式配置负载均衡策略方法
 * final OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/demo");
 * factory.setConnectionStrategy(OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_CONNECT.toString());
 * OrientGraphNoTx graph = factory.getNoTx();
 * <p>
 * 含有该方法的API在 orientdb-graphdb jar中，而在 3.0.27版本的 orientdb-jdbc 中没有了 orientdb-graphdb的依赖了
 *
 * @author XYL
 * @title: demo4
 * @description: TODO
 * @date 2020/2/17 17:05
 * @return
 * @since V1.0
 */
public class demo4 {

    public static void main(String[] args) {
        System.setProperty("log.console.level", "FINE");

        String url = "remote:119.18.198.137:10600/xyl;remote:119.18.198.137:10601/xyl";

//        OrientGraph graph = new OrientGraph(url, "root", "password");
//
//        graph.setConnectionStrategy(OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_REQUEST.toString());

        //1. 获取工厂
        OrientGraphFactory factory = new OrientGraphFactory(url);

        factory.setConnectionStrategy(OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_REQUEST.toString());

        Object property = factory.getProperty(OGlobalConfiguration.CLIENT_CONNECTION_STRATEGY.getKey());

        System.out.println("工厂中负载均衡策略：" + property);

        OrientGraphNoTx graph = factory.getNoTx();

        OrientConfigurableGraph.THREAD_MODE threadMode = graph.getThreadMode();

        String name = threadMode.name();

        System.out.println("线程模式:" + name);

        String name1 = graph.getRawGraph().getDatabaseOwner().getName();

        String status = graph.getRawGraph().getDatabaseOwner().getStatus().toString();

        System.out.println("数据库状态：" + status);

        System.out.println("数据库名称：" + name1);

        String url1 = graph.getRawGraph().getDatabaseOwner().getURL();

        System.out.println("当前连接数据节点：" + url1);

        int defaultClusterId = graph.getRawGraph().getDatabaseOwner().getDefaultClusterId();

        System.out.println("默认clusterID：" + defaultClusterId);

        String localNodeName = graph.getRawGraph().getDatabaseOwner().getLocalNodeName();

        System.out.println("本地节点名称：" + localNodeName);

        String connectionStrategy = graph.getConnectionStrategy();

        System.out.println("当前连接策略：" + connectionStrategy);

        Iterable<Vertex> person = graph.getVerticesOfClass("person");

        person.forEach(x ->
                System.out.println(x.getId().toString())
        );

    }

}
