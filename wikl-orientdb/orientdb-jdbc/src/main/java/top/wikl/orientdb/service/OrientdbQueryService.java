package top.wikl.orientdb.service;

import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import top.wikl.entity.graph.output.WiklResultData;
import top.wikl.orientdb.config.WiklOrientDbProperty;
import top.wikl.orientdb.entity.graph.vo.OrientDBJdbcResultVo;
import top.wikl.orientdb.utils.WiklGraphResultUtil;
import top.wikl.orientdb.utils.WiklOrientdbUtil;

import java.util.Map;


/**
 * orientdb
 * @param
 * @author XYL
 * @date 2019/9/27 13:51
 * @return
 * @since  V1.0
 */
public interface OrientdbQueryService {

    /**
     * orientdb查询方法
     *
     * @param orientDbProperty
     * @param sql
     * @param params
     * @param limit
     * @return
     */
    default WiklResultData execete(WiklOrientDbProperty orientDbProperty, String sql, Map params, int limit) {

        //1.创建客户端
        OrientDB orientDB = WiklOrientdbUtil.createClient(orientDbProperty.getJdbcurl());

        //2. 建立连接
        ODatabasePool pool = WiklOrientdbUtil.connect(orientDB, orientDbProperty.getDatabase(), orientDbProperty.getUsername(), orientDbProperty.getPassword());

        OResultSet rs;

        try (ODatabaseSession session = pool.acquire()) {

            //处理当前线程中数据库实例不可用情况
            ODatabase oDatabase = WiklOrientdbUtil.activeOnCurrentThread(session);

            //执行查询
            rs = oDatabase.query(sql, params);
        }

        //结果集封装
        WiklResultData resultData = WiklGraphResultUtil.JdbcResult(rs, limit);

        //关闭资源
        WiklOrientdbUtil.close(rs, pool, orientDB);

        return resultData;
    }

    /**
     * orientdb查询方法
     *
     * @param orientDbProperty
     * @return
     */
    default OrientDBJdbcResultVo execute(WiklOrientDbProperty orientDbProperty) {

        //结果对象
        OrientDBJdbcResultVo orientDBJdbcResultVo = new OrientDBJdbcResultVo();

        long startConnect = System.currentTimeMillis();

        System.out.println("打开数据库连接：" + startConnect);

        //1.创建客户端
        OrientDB orientDB = WiklOrientdbUtil.createClient(orientDbProperty.getJdbcurl());

        //2. 建立连接
        ODatabasePool pool = WiklOrientdbUtil.connect(orientDB, orientDbProperty.getDatabase(), orientDbProperty.getUsername(), orientDbProperty.getPassword());

        orientDBJdbcResultVo.setODatabasePool(pool);
        orientDBJdbcResultVo.setOrientDB(orientDB);

        return orientDBJdbcResultVo;
    }

}
