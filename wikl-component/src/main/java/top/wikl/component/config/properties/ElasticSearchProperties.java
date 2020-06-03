package top.wikl.component.config.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@ConfigurationProperties(prefix = "elastic", ignoreUnknownFields = false)
@PropertySource(value = {"classpath:application-${spring.profiles.active}.yml"}, encoding = "utf-8")
@Slf4j
@Data
@Component
public class ElasticSearchProperties {

    private String host;

    private Integer port;

    private String username;

    private String password;
}
