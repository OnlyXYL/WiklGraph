package top.wikl.neo4j.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author XYL
 * @title: Neo4jPropertyConfig
 * @description: neo4j相关属性配置
 * @date 2019/10/30 18:34
 * @return
 * @since V1.0
 */
@ConfigurationProperties(prefix = "storage.neo4j", ignoreUnknownFields = false)
@PropertySource(value = "classpath:application-dev.yml", encoding = "utf-8")
@Data
@Configuration
public class Neo4jPropertyConfig {

    /**
     * url
     */
    private String url;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;
}
