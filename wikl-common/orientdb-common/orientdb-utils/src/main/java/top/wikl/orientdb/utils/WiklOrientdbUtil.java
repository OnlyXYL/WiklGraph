package top.wikl.orientdb.utils;

import com.orientechnologies.orient.core.db.*;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraphFactory;

import java.util.Objects;

/**
 * orientdb 数据库连接工具类
 *
 * @param
 * @author XYL
 * @date 2019/9/27 10:39
 * @return
 * @since V1.0
 */
public class WiklOrientdbUtil {

    /**
     * orientdb 创建客户端
     *
     * @param url like--->remote:10.0.47.51
     * @return
     */
    public static OrientDB createClient(String url) {
        OrientDB orient = new OrientDB(url, OrientDBConfig.defaultConfig());

        return orient;
    }

    /**
     * orientdb，建立连接
     *
     * @param database
     * @param userName
     * @param passWord
     * @return
     */
    public static ODatabasePool connect(OrientDB orient, String database, String userName, String passWord) {

        ODatabasePool pool = new ODatabasePool(orient, database, userName, passWord);

        return pool;
    }

    /**
     * 数据库资源关闭操作
     *
     * @param oResultSet
     */
    public static void close(OResultSet oResultSet) {

        if (!Objects.isNull(oResultSet)) {

            oResultSet.close();
        }
    }

    /**
     * 数据库资源关闭操作
     *
     * @param oResultSet
     * @param pool
     * @param orientDB
     */
    public static void close(OResultSet oResultSet, ODatabasePool pool, OrientDB orientDB) {

        if (!Objects.isNull(oResultSet)) {

            oResultSet.close();
        }

        if (!Objects.isNull(pool)) {
            pool.close();
        }

        if (!Objects.isNull(orientDB)) {
            orientDB.close();
        }

    }

    /**
     * 数据库资源关闭操作
     *
     * @param oResultSet
     * @param pool
     * @param orientDB
     */
    public static void close(OResultSet oResultSet, ODatabasePool pool, OrientDB orientDB, ODatabaseSession session) {

        if (!Objects.isNull(oResultSet)) {

            oResultSet.close();
        }

        if (!Objects.isNull(session)) {
            session.close();
        }

        if (!Objects.isNull(pool)) {
            pool.close();
        }

        if (!Objects.isNull(orientDB)) {
            orientDB.close();
        }

    }

    /**
     * 判断数据库连接势力在当前线程中是否可用，返回可用实例对象
     *
     * @param databaseSession
     * @return
     */
    public static ODatabase activeOnCurrentThread(ODatabaseSession databaseSession) {

        //获取当前线程中数据库实例是否可用状态
        boolean active = databaseSession.isActiveOnCurrentThread();

        if (!active) {

            //active instance on current thread
            ODatabase oDatabase = databaseSession.activateOnCurrentThread();

            return oDatabase;

        } else {
            return databaseSession;
        }

    }

    /**
     * 打开数据库连接，有事务
     *
     * @param orientGraphFactory
     * @return
     */
    public static OrientGraph openTx(OrientGraphFactory orientGraphFactory) {

        OrientGraph noTx = orientGraphFactory.getTx();

        return noTx;
    }


    /**
     * 打开数据库连接，没有事务
     *
     * @param orientGraphFactory
     * @return
     */
    public static OrientGraph openNoTx(OrientGraphFactory orientGraphFactory) {

        OrientGraph noTx = orientGraphFactory.getNoTx();

        return noTx;
    }

    /**
     * 事务提交， 关闭数据库连接
     *
     * @param orientGraph
     */
    public static void commitAndClose(OrientGraph orientGraph) {

        orientGraph.commit();

        orientGraph.close();
    }

    /**
     * 事务提交， 关闭数据库连接
     *
     * @param orientGraph
     */
    public static void commit(OrientGraph orientGraph) {
        orientGraph.commit();
    }

    /**
     * 关闭数据库连接
     *
     * @param orientGraph
     */
    public static void close(OrientGraph orientGraph) {

        orientGraph.close();
    }


}
