package top.wikl.component.config.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author XYL
 * @title: ElasticSearchProperties
 * @description: TODO
 * @date 2020/6/2 18:32
 * @return
 * @since V1.0
 */
@ConfigurationProperties(prefix = "spring.elasticsearch", ignoreUnknownFields = false)
@PropertySource(value = {"classpath:application.yml"}, encoding = "utf-8")
@Slf4j
@Data
@SpringBootConfiguration
public class ElasticSearchProperties {

    private String uris;

    private String username;

    private String password;
}
