package top.wikl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author XYL
 * @title: TraceaApplication
 * @description: TODO
 * @date 2020/5/31 16:05
 * @return
 * @since V1.0
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class TraceaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraceaApplication.class);
    }

}
