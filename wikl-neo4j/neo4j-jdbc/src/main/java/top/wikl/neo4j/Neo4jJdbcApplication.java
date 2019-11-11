package top.wikl.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author XYL
 * @Title: Neo4jJdbcApplication
 * @ProjectName WiklGraph
 * @Description: TODO
 * @date 2019/4/2719:14
 */
@EnableFeignClients
@EnableSwagger2
@SpringBootApplication
public class Neo4jJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Neo4jJdbcApplication.class,args);
    }
}
