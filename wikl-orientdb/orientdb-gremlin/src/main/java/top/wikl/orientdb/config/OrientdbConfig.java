package top.wikl.orientdb.config;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XYL
 * @title: OrientdbManage
 * @description: TODO
 * @date 2020/4/29 10:53
 * @return
 * @since V1.0
 */
@Configuration
public class OrientdbConfig {

    @Bean
    public OrientGraphFactory orientGraphFactory() {

        OrientGraphFactory orientGraphFactory = new OrientGraphFactory("")
                .setupPool(1, 10);

        return orientGraphFactory;

    }
}
