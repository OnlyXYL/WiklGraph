package top.wikl.orientdb;

import com.orientechnologies.orient.client.remote.OStorageRemote;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import java.util.Objects;

/**
 * 插入操作，负载均衡方法：
 * <p>
 * 配置：
 * city_node101: ["node101","node102","node103"]
 * city_node104: ["node104","node105","node106"]
 * <p>
 * 前提：通过指定cluster 实现数据的分片
 * <p>
 * 方法：
 * <p>
 * --------------數據在節點101,102,103上
 * <p>
 * 第一步：創建cluster                create cluster city_node101
 * 第二部：創建class，並制定cluster,   create class city extends v cluster city_node101`id
 * 第三部：創建點                     create vertex city cluster city_node101
 * <p>
 * --------------數據在節點104，105,106 上
 * <p>
 * 第四步：創建另一個cluster,並把cluster添加到想要分片的class上    alter class city addcluster city_node104
 * 第五步：創建點 create vertex city cluster city_node104
 *
 * @author XYL
 * @title: demo8
 * @description: TODO
 * @date 2020/2/20 14:34
 * @return
 * @since V1.0
 */
public class demo8 {

    public static void main(String[] args) {

//        String url = "remote:119.18.198.137:10600,119.18.198.137:10601";

        String url = "remote:10.0.43.101,10.0.43.102,10.0.43.103,10.0.43.104,10.0.43.105,10.0.43.106";
//        String url = "remote:10.0.43.103,10.0.43.106";

        OrientDBConfig config = OrientDBConfig.builder()
                //设置负载均衡策略
                .addConfig(OGlobalConfiguration.CLIENT_CONNECTION_STRATEGY, OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_REQUEST.toString())
                //设置session超时时间
                .addConfig(OGlobalConfiguration.NETWORK_TOKEN_EXPIRE_TIMEOUT, 30)
                .build();

        OrientDB orient = new OrientDB(url, config);

        ODatabaseSession session = orient.open("xyl", "root", "password");

        session.getLocalCache().invalidate();

        session.getLocalCache().clear();

        String cluster = "city_node104";

        String aClass = "city";

        try {
            //调用方法
            execute(session, aClass, cluster);
            System.out.println("写入成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("写入失败");
        } finally {

            session.close();
            orient.close();
        }

    }

    /**
     * @param
     * @return
     * @author XYL
     * @date 2020/2/21 10:37
     * @since V1.0
     */
    public static void execute(ODatabaseSession session, String aClass, String cluster) {

        String sql = "select from " + aClass;

        //判断cluster 是否存在
        boolean exit = session.existsCluster(cluster);

        System.out.println("是否存在cluster: " + exit);

        //clusterId
        int clusterId = 0;

        boolean flag = false;

        //不存在，执行新建
        if (!exit) {

            //cluster不存在，新建cluster加到当前class中
            flag = true;

            /**
             *<p>session.addCluster(cluster);</p>
             *
             * 此方法，在连接6个节点时，执行新建cluster 报错
             *
             * com.orientechnologies.orient.server.distributed.ODistributedException: Error on creating cluster 'person_node104' on distributed nodes: local and remote ids assigned are different
             *
             */

            OResultSet command = session.command("create cluster " + cluster);

            session.commit();

            command.close();

            clusterId = session.getClusterIdByName(cluster);

            System.out.println("cluster id ：" + clusterId);
        } else {
            clusterId = session.getClusterIdByName(cluster);
        }

        //判断cluster 是否存在
        boolean exit_ = session.existsCluster(cluster);

        System.out.println("是否存在cluster: " + exit_);

        //判断是否存在class
        OClass oClass = session.getClass(aClass);

        //class 不存在,新建class 并指定 cluster
        if (Objects.isNull(oClass)) {

            //执行新建class
            String createClassSql = "create class " + aClass + " extends v cluster " + clusterId;

            OResultSet resultSet = session.command(createClassSql);
            session.commit();
            resultSet.close();
        } else {

            //新增的cluster 执行添加操作
            if (flag) {

                //1. 先增加cluster
                //新增cluster
                String addClusterSql = "alter class " + aClass + " addcluster " + cluster;

                OResultSet resultSet = session.command(addClusterSql);
                session.commit();
                resultSet.close();

                System.out.println("给class 新增 cluster 成功");
            }
        }

        //创建vertex
        String createVertexSql = "create vertex " + aClass + " cluster " + cluster;

        //创建点
        OResultSet resultSet_ = session.command(createVertexSql);

        resultSet_.close();

        //根据cluster 执行查询
        OResultSet query = session.query("select from cluster:" + cluster);

        if (query.hasNext()) {
            System.out.println("通过 Cluster 查询数据成功!");
            query.close();
        } else {
            System.out.println("没有数据！");
        }
    }
}
