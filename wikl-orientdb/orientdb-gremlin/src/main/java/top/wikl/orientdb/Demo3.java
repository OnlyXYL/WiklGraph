package top.wikl.orientdb;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import java.util.Objects;

/**
 * 验证：
 * 1：在 102 节点上，创建cluster  client_node102
 * 2：在 102 节点上，执行 create class city  extends cluster client_node102`Id ;  create vertex  city cluster client_node102
 * 3：查询看结果
 * 4：在 102 上，执行 create vertex person cluster client_node101， 查看结果
 *
 * @author XYL
 * @title: Demo3
 * @description: TODO
 * @date 2020/2/13 16:45
 * @return
 * @since V1.0
 */
public class Demo3 {
    public static void main(String[] args) {

        OrientDB orient = connect("10.0.43.102");

        ODatabaseSession session = orient.open("xyl", "root", "password");

        //2. 建立连接
//        ODatabasePool pool = new ODatabasePool(orient, "xyl", "root", "password");

        String cluster = "client_node102";

        String aClass = "city";

        //1. 102相关操作
        execute(session, aClass, cluster);

        //101相关操作
        String cluster_ = "client_node101";

        String aClass_ = "person";

        //1. 102相关操作
//        execute(session, aClass_, cluster_);

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
    public static OrientDB connect(String url) {

        String _url = "remote:" + url;

        _url = "remote:10.0.43.102,remote:10.0.43.101,remote:10.0.43.103,remote:10.0.43.104,remote:10.0.43.105,remote:10.0.43.106";

        //1.创建客户端
        OrientDB orient = new OrientDB(_url, OrientDBConfig.defaultConfig());

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
