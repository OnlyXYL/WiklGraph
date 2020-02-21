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
 * @author XYL
 * @title: demo8
 * @description: TODO
 * @date 2020/2/20 14:34
 * @return
 * @since V1.0
 */
public class demo8 {

    public static void main(String[] args) {

        String url = "remote:119.18.198.137:10600,119.18.198.137:10601";

        OrientDBConfig config = OrientDBConfig.builder()
                //设置负载均衡策略
                .addConfig(OGlobalConfiguration.CLIENT_CONNECTION_STRATEGY, OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_REQUEST.toString())
                //设置session超时时间
                .addConfig(OGlobalConfiguration.NETWORK_TOKEN_EXPIRE_TIMEOUT, 30)
                .build();

        OrientDB orient = new OrientDB(url, config);

        ODatabaseSession session = orient.open("xyl", "root", "password");

        String cluster = "person_node104";

        String aClass = "person";

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

        //不存在，执行新建
        if (!exit) {

            clusterId = session.addCluster(cluster);

            System.out.println("cluster id ：" + clusterId);
        } else {
            clusterId = session.getClusterIdByName(cluster);
        }

        //判断cluster 是否存在
        boolean exit_ = session.existsCluster(cluster);

        System.out.println("是否存在cluster: " + exit_);

        //判断是否存在class
        OClass oClass = session.getClass(aClass);

        //class 不存在
        if (Objects.isNull(oClass)) {

            //执行新建class
            String createClassSql = "create class " + aClass + " extends v cluster " + clusterId;

            OResultSet resultSet = session.command(createClassSql);

            resultSet.close();
        }

        //判断是否存在class
        OClass oClass_ = session.getClass(aClass);

        if (Objects.nonNull(oClass_)) {

            //创建vertex
            String createVertexSql = "create vertex " + aClass + " cluster " + cluster;

            //创建点
            OResultSet resultSet = session.command(createVertexSql);

            resultSet.close();

        }

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
