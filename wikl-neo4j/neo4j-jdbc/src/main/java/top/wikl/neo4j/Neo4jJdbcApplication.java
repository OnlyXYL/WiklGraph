package top.wikl.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @EnableEurekaClient 和 @EnableDiscoveryClient 区别
 *
 * 相同点：在启动类上添加之后，都可以让该服务注册到注册中心上
 * 不同点：@EnableEurekaClient 只支持Eureka注册中心，@EnableDiscoveryClient 支持Eureka ,Zookeeper, Consule 三个注册中心
 *
 * 在 SpringCloud 中，服务发现组件有多种选择，例如Zookeeper,Consule等。@EnableDiscoveryClient 为各种服务组件提供了支持，该注解是 spring-cloud-commons 项目的注解，是一个高度的抽象；
 * 而 @EnableEurekaClient 表明是 Eureka的Client，该注解是 spring-cloud-netflix项目中的注解，只能与Eureka一起工作。当Eureka在项目的 classpath中时，两个注解没有区别
 *
 * @author XYL
 * @Title: Neo4jJdbcApplication
 * @ProjectName WiklGraph
 * @Description: TODO
 * @date 2019/4/2719:14
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@SpringBootApplication
public class Neo4jJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Neo4jJdbcApplication.class, args);
    }
}
