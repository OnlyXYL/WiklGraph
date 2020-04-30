package top.wikl.orientdb.demo;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import java.util.Objects;

/**
 * 验证：
 * 1：在 10.0.43.101 上新建 cluster  client_node101          create  cluste client_node101
 * 2：在 10.0.43.101 上新建 class     create class person  extends v cluster  client_node101`ID
 * 3：在 10.0.43.101 上 新建点   create vertex person cluster client_node101
 * 4：在101 节点上执行查询
 * 5：在 102，103 节点上执行查询
 * <p>
 * <p>
 * <p>
 * 改进
 * <p>
 * 1)	修改配置文件 增加 client_node106, client_node107, client_node108 cluster 信息
 * 2)	连接101 节点
 * <p>
 * 	创建新的cluster    Create cluster client_node106
 * 	创建class    Create class employee extends v cluster client_node106`ID
 * 	创建点      Create vertex employee cluster client_node106
 * <p>
 * 3)	连接102 节点
 * <p>
 * 	创建新的cluster  Create cluster client_node107
 * 	给class employee 增加新的 cluster   alter class employee addcluster client_node107
 * 	创建点  create vertex employee cluster client_node107
 * <p>
 * <p>
 * 4)	连接103 节点
 * <p>
 * 	操作同102 节点  cluster 为  client_node108
 *
 * @author XYL
 * @title: Demo1
 * @description: TODO
 * @date 2020/2/6 16:05
 * @return
 * @since V1.0
 */
public class Demo1 {

    public static void main(String[] args) {

        OrientDB orient = connect("10.0.43.101");

        ODatabaseSession session = orient.open("xyl", "root", "password");

        //2. 建立连接
//        ODatabasePool pool = new ODatabasePool(orient, "xyl", "root", "password");

        String cluster = "client_node106";

        String aClass = "employee";

        //1. 查询
        execute(session, aClass, cluster);
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

//        _url = "remote:10.0.43.101,remote:10.0.43.102,remote:10.0.43.103,remote:10.0.43.104,remote:10.0.43.105,remote:10.0.43.106";

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

        clusterId = session.getClusterIdByName(cluster);

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

        session.getLocalCache().invalidate();

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

        session.close();

    }

}
