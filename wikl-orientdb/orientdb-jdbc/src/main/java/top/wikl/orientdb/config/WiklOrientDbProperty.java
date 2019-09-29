package top.wikl.orientdb.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * orientdb 数据库配置
 *
 * @param
 * @author XYL
 * @date 2019/9/27 10:38
 * @return
 * @since V1.0
 */
@ConfigurationProperties(prefix = "storage.orientdb", ignoreUnknownFields = false)
@PropertySource(value = {"classpath:application.yml"}, encoding = "utf-8")
@Slf4j
@Data
@Component
public class WiklOrientDbProperty {

    private String url;

    private String jdbcurl;

    private String database;

    private String username;

    private String password;
}
