package top.wikl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author XYL
 * @title: TraceaController
 * @description: TODO
 * @date 2020/6/1 13:41
 * @return
 * @since V1.0
 */
@RestController
@RequestMapping("/trace_a")
public class TraceaController {

    @Autowired
    private ReactorLoadBalancerExchangeFilterFunction lbFunction;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://trace-b")
                .filter(lbFunction)
                .build();
    }


    @GetMapping("/a")
    public Mono<String> trace() {
        System.out.println("===call trace-a===");

        return webClient().get()
                .uri("/trace-b")
                .retrieve()
                .bodyToMono(String.class);
    }

}
