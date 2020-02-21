package top.wikl.orientdb;

import com.orientechnologies.orient.client.remote.OStorageRemote;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import java.util.List;
import java.util.Objects;

/**
 * 验证：
 * 1：在102上，执行 select from cluster: client_node101 ,查看结果
 * <p>
 * 结果：如果能查到数据，证明分片有意义，反之，个人感觉意义不大
 *
 * @author XYL
 * @title: demo2
 * @description: TODO
 * @date 2020/2/13 16:35
 * @return
 * @since V1.0
 */
public class demo2 {

    public static void main(String[] args) {

        OrientDBConfig build = OrientDBConfig.builder()
                .addConfig(OGlobalConfiguration.CLIENT_CONNECTION_STRATEGY, OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_REQUEST.toString())
                .addConfig(OGlobalConfiguration.NETWORK_TOKEN_EXPIRE_TIMEOUT, 10)
                .build();

        String url = "remote:10.0.43.101,10.0.43.102,10.0.43.103,10.0.43.104,10.0.43.105,10.0.43.106";

        OrientDB orient = connect(url, build);

        List<String> list = orient.list();

        ODatabaseSession session = orient.open("xyl", "root", "password", build);

        String databaseUrl = session.getURL();

        System.out.println("已连接数据库地址：" + databaseUrl);

        //2. 建立连接
//        ODatabasePool pool = new ODatabasePool(orient, "xyl", "root", "password");

        String cluster = "client_node102";

        String aClass = "person";

        //1. 查询
        execute(session, aClass, cluster);

        session.close();
    }

    /**
     * 连接
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/2/6 17:47
     * @since V1.0
     */
    public static OrientDB connect(String url, OrientDBConfig config) {

        //1.创建客户端
        OrientDB orient = new OrientDB(url, config);

        return orient;
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

        //class存在
        if (Objects.nonNull(oClass_)) {

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

}
