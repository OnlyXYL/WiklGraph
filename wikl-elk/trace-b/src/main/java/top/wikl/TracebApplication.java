package top.wikl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author XYL
 * @title: TracebApplication
 * @description: TODO
 * @date 2020/5/31 16:09
 * @return
 * @since V1.0
 */
@EnableEurekaClient
@RestController
@SpringBootApplication
public class TracebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TracebApplication.class);
    }

    @GetMapping("/trace-b")
    public Mono<String> trace() {
        System.out.println("===call trace-b===");

        return Mono.just("Trace");
    }
}
