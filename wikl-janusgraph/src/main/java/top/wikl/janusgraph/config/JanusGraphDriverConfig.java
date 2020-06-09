package top.wikl.janusgraph.config;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XYL
 * @title: JanusGraphDriverConfig
 * @description: TODO
 * @date 2019/12/10 15:21
 * @return
 * @since V1.0
 */
@Configuration
public class JanusGraphDriverConfig {

    @Bean
    public JanusGraph janusGraph() {

        JanusGraph graph = JanusGraphFactory.open("cql:10.0.41.22");

        return graph;

    }
}
