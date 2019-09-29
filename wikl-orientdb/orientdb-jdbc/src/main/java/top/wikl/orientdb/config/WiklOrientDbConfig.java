package top.wikl.orientdb.config;

import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.OrientDB;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraphFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wikl.orientdb.utils.WiklOrientdbUtil;

import javax.annotation.Resource;

/**
 * orientdb 相关的配置
 *
 * @param
 * @author XYL
 * @date 2019/9/27 10:45
 * @return
 * @since V1.0
 */
@Configuration
public class WiklOrientDbConfig {

    @Resource
    private WiklOrientDbProperty orientDbProperty;

    @Bean
    public OrientGraphFactory orientGraphFactory() {

        OrientGraphFactory orientGraphFactory = new OrientGraphFactory(orientDbProperty.getUrl(), orientDbProperty.getUsername(), orientDbProperty.getPassword()).setupPool(1, 100);

        return orientGraphFactory;
    }

    @Bean
    public ODatabasePool oDatabasePool() {
        //1.创建客户端
        OrientDB orientDB = WiklOrientdbUtil.createClient(orientDbProperty.getJdbcurl());

        //2. 建立连接
        ODatabasePool pool = WiklOrientdbUtil.connect(orientDB, orientDbProperty.getDatabase(), orientDbProperty.getUsername(), orientDbProperty.getPassword());

        return pool;

    }

}
