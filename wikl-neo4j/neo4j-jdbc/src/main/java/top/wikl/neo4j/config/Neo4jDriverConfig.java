package top.wikl.neo4j.config;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wikl.neo4j.config.properties.Neo4jPropertyConfig;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author XYL
 * @title: Neo4jConfig
 * @description: neo4j驱动相关的配置
 * @date 2019/10/30 18:30
 * @return
 * @since V1.0
 */
@Configuration
public class Neo4jDriverConfig {

    @Resource
    private Neo4jPropertyConfig neo4jPropertyConfig;

    @Bean
    public Driver driver(){

        /**
         * 配置信息
         */
        Config config = Config.builder()
                .withMaxConnectionLifetime( 30, TimeUnit.MINUTES )
                .withMaxConnectionPoolSize( 50 )
                .withConnectionAcquisitionTimeout( 2, TimeUnit.MINUTES )

                /**
                 * During a TLS handshake, the server provides a certificate to the client application. The application can choose to accept or reject this certificate based on one of the following trust strategies:
                 *
                 * {@link https://neo4j.com/docs/driver-manual/current/client-applications/#driver-connection-uris}
                 *
                 * TRUST_ALL_CERTIFICATES (default)    ----------  Accept any certificate provided by the server, regardless of CA chain.
                 *
                 * TRUST_CUSTOM_CA_SIGNED_CERTIFICATES  ----------  Accept any certificate that can be verified against a custom CA.
                 *
                 * TRUST_SYSTEM_CA_SIGNED_CERTIFICATES  ----------   Accept any certificate that can be verified against the system store.
                 *
                 */
                .withTrustStrategy( Config.TrustStrategy.trustAllCertificates() )
                .build();

        Driver driver = GraphDatabase.driver(neo4jPropertyConfig.getUrl(), AuthTokens.basic(neo4jPropertyConfig.getUserName(), neo4jPropertyConfig.getPassword()),config);

        return driver;
    }
}
