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
 * 改进后的节点102 和节点103 上的操作
 * <p>
 * 	创建新的cluster  Create cluster client_node107
 * 	给class employee 增加新的 cluster   alter class employee addcluster client_node107
 * 	创建点  create vertex employee cluster client_node107
 *
 * @author XYL
 * @title: demo5
 * @description: TODO
 * @date 2020/2/17 23:06
 * @return
 * @since V1.0
 */
public class demo5 {

    public static void main(String[] args) {

        String _url = "remote:10.0.43.101,10.0.43.102,10.0.43.103,10.0.43.104,10.0.43.105,10.0.43.106";

        OrientDBConfig config = OrientDBConfig.builder()
                //设置负载均衡策略
                .addConfig(OGlobalConfiguration.CLIENT_CONNECTION_STRATEGY, OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_REQUEST.toString())
                //设置session超时时间
                .addConfig(OGlobalConfiguration.NETWORK_TOKEN_EXPIRE_TIMEOUT, 30)
                .build();

        OrientDB orient = new OrientDB(_url, config);

        ODatabaseSession session = orient.open("xyl", "root", "password");

        String cluster = "city_node101";

        String aClass = "city";

        OResultSet command = session.command("select from cluster:" + cluster);
        while (command.hasNext()) {
            System.out.println("通过 Cluster 查询数据成功!");
            command.close();
        }

        //1. 查询
//        execute(session, aClass, cluster);

        session.close();

        orient.close();
    }

    /**
     * 查询
     *
     * @param session
     * @param aClass
     * @param cluster
     * @return
     * @author XYL
     * @date 2020/2/6 17:43
     * @since V1.0
     */
    public static void execute(ODatabaseSession session, String aClass, String cluster) {

        String sql = "select from " + aClass;

        //查询数据

        //判断cluster 是否存在
        boolean exit = session.existsCluster(cluster);

        System.out.println("是否存在cluster: " + exit);

        //clusterId
        int clusterId = 0;

        //不存在，执行新建
        if (!exit) {

            clusterId = session.addCluster(cluster);

            System.out.println("cluster id ：" + clusterId);
        }


        //判断是否存在class
        OClass oClass_ = session.getClass(aClass);

        //给class 增加新的cluster
        if (Objects.nonNull(oClass_)) {

            //新增cluster
            String addClusterSql = "alter class " + aClass + " addcluster " + cluster;

            OResultSet resultSet = session.command(addClusterSql);

            resultSet.close();

            System.out.println("给class 新增 cluster 成功");

        }

        //新增点
        String createVertexSql = "create vertex " + aClass + " cluster " + cluster;

        //新增点
        OResultSet rs = session.command(createVertexSql);

        rs.close();

        //根据class  执行查询
        OResultSet resultSet = session.query(sql);

        if (resultSet.hasNext()) {

            System.out.println("有数据！");
            resultSet.close();
        } else {
            System.out.println("没有数据！");
        }

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