package top.wikl.neo4j.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/4/20 0020 15:27
 */
@RefreshScope
@ConfigurationProperties(prefix = "test.auto", ignoreUnknownFields = false)
@Data
@Configuration
public class TestAutoRefreshProperties {

    private String name;

    private String sex;

    private Integer age;
}
