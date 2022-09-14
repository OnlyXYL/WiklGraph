package top.wikl.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * email配置
 *
 * @author XYL
 * @version 1.2
 * @since 2021/9/18 0018 14:58
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:datagovern-auth.properties"})
@ConfigurationProperties(prefix = "datagovern.email")
public class MailProperties {

    private String host;
    private String from;
    private String password;
    private String ecoding;
}
