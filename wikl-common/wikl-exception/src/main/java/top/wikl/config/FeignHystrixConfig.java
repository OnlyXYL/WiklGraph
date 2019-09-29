package top.wikl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * 系统配置
 *
 * @author WangYuxiao
 * @date 18/6/25
 */
@ConfigurationProperties(prefix = "feign.hystrix")
@Data
@Configuration
@PropertySource("classpath:application.yml")
public class FeignHystrixConfig {


     private boolean enabled;


}
