package top.wikl.janusgraph.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author XYL
 * @title: JanusGraphConfig
 * @description: TODO
 * @date 2019/12/10 15:07
 * @return
 * @since V1.0
 */
@ConfigurationProperties(prefix = "storage.janusgraph", ignoreUnknownFields = false)
@PropertySource(value = "classpath:application.yml", encoding = "utf-8")
@Data
@Configuration
public class JanusGraphConfig {

    private String url;
}
