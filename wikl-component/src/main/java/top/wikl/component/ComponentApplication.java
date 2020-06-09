package top.wikl.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * @author XYL
 * @title: ComponentApplication
 * @description: TODO
 * @date 2019/12/17 14:24
 * @return
 * @since V1.0
 */
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"top.wikl.*","top.wikl.component.*"})
public class ComponentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComponentApplication.class, args);
    }
}
