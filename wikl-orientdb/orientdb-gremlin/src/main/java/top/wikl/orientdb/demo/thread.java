package top.wikl.orientdb.demo;

import com.orientechnologies.orient.client.remote.OStorageRemote;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

/**
 * @author XYL
 * @title: thread
 * @description: TODO
 * @date 2020/6/24 10:39
 * @return
 * @since V1.2
 */
public class thread {

    public static void main(String[] args) {
        try {
            String _url = "remote:10.0.43.121";

            OrientDBConfig config = OrientDBConfig.builder()
                    //设置负载均衡策略
                    .addConfig(OGlobalConfiguration.CLIENT_CONNECTION_STRATEGY, OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_REQUEST.toString())
                    //设置session超时时间
                    .addConfig(OGlobalConfiguration.NETWORK_TOKEN_EXPIRE_TIMEOUT, 30)
                    .addConfig(OGlobalConfiguration.DB_POOL_MAX, 20)
                    .addConfig(OGlobalConfiguration.DB_POOL_MIN, 10)
                    .build();

            OrientDB orient = new OrientDB(_url, config);

            ODatabasePool pool = new ODatabasePool(orient, "test", "root", "Bmzt2016_orientdb");

            Thread thread1 = new Thread(() -> {

                try (ODatabaseSession session = pool.acquire();) {

                    String sql = " CREATE CLASS person extends v;"
                            + " CREATE PROPERTY person.name string;"
                            + " CREATE CLASS company extends v;"
                            + " CREATE PROPERTY company.name string;"
                            + " CREATE INDEX person.name UNIQUE_HASH_INDEX; "
                            + " CREATE INDEX company.name UNIQUE_HASH_INDEX;"
                            + " CREATE CLASS work extends e;"
                            + " CREATE PROPERTY work.out link person;"
                            + " CREATE PROPERTY work.in link company;"
                            + " CREATE INDEX work.out_in on work (out,in) UNIQUE_HASH_INDEX;";

                    OResultSet resultSet = session.execute("sql", sql);

                    resultSet.close();
                }
            });

            Thread thread = new Thread(() -> {

                try (ODatabaseSession session = pool.acquire();) {

                    String sql = " CREATE CLASS person1 extends v;"
                            + " CREATE PROPERTY person1.name string;"
                            + " CREATE CLASS company1 extends v;"
                            + " CREATE PROPERTY company1.name string;"
                            + " CREATE INDEX person1.name UNIQUE_HASH_INDEX; "
                            + " CREATE INDEX company1.name UNIQUE_HASH_INDEX;"
                            + " CREATE CLASS work1 extends e;"
                            + " CREATE PROPERTY work1.out link person1;"
                            + " CREATE PROPERTY work1.in link company1;"
                            + " CREATE INDEX work1.out_in on work1 (out,in) UNIQUE_HASH_INDEX;";

                    OResultSet resultSet = session.execute("sql", sql);

                    resultSet.close();
                }
            });

            thread1.start();
            thread.start();

            thread1.join();

            thread.join();

            System.out.println("多线程创建 schema 结束");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
