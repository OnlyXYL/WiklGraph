package top.wikl.orientdb.demo;

import com.orientechnologies.orient.client.remote.OStorageRemote;
import com.orientechnologies.orient.core.config.OContextConfiguration;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

/**
 * 压力测试方式验证负载均衡----非工厂方式
 *
 * @author XYL
 * @title: demo7
 * @description: TODO
 * @date 2020/2/19 21:33
 * @return
 * @since V1.0
 */
public class demo7 {

    public static void main(String[] args) {

        String url = "remote:119.18.198.137:10601,119.18.198.137:10600";

        OrientDBConfig config = OrientDBConfig.builder()
                //设置负载均衡策略
                .addConfig(OGlobalConfiguration.CLIENT_CONNECTION_STRATEGY, OStorageRemote.CONNECTION_STRATEGY.ROUND_ROBIN_REQUEST.toString())
                //设置session超时时间
                .addConfig(OGlobalConfiguration.NETWORK_TOKEN_EXPIRE_TIMEOUT, 30)
                .build();

        OrientDB orient = new OrientDB(url, config);

        ODatabaseSession session = orient.open("xyl", "root", "password");

        //线程数
        int threadNum = 1000;

        //调用方法
        multiThread(session, threadNum);

        session.close();
        orient.close();
    }


    /**
     * 多线程测试负载均衡
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/2/19 22:17
     * @since V1.0
     */
    private static void multiThread(ODatabaseSession session, int threadNum) {

        for (int i = 0; i < threadNum; i++) {

            new Thread(() -> {

                //获取配置信息
                OContextConfiguration configuration = session.getConfiguration();

                String url = session.getURL();

                System.out.println("节点地址：" + url);

                //获取负载均衡方式
                Object value = configuration.getValue(OGlobalConfiguration.CLIENT_CONNECTION_STRATEGY);

                System.out.println("连接策略：" + value);

                Object timeout = configuration.getValue(OGlobalConfiguration.NETWORK_TOKEN_EXPIRE_TIMEOUT);

                System.out.println("超时时间：" + timeout + " 分钟");

                OResultSet resultSet = session.command("select from person");

                resultSet.stream().forEach(x ->
                        System.out.println(x.getIdentity().toString())
                );

            }).run();

            System.out.println(" ");
            System.out.println("----------------");
            System.out.println(" ");
        }
    }

}
