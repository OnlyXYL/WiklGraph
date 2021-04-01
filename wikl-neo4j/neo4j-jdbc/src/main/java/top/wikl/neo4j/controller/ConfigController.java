package top.wikl.neo4j.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置中心验证
 *
 * @author XYL
 * @version 1.2
 * @since 2020/11/18 14:15
 */
@RefreshScope
@RequestMapping("/neo4j/config")
@RestController
public class ConfigController {

//    @Value("${foo}")
    private String foo;

    @GetMapping(value = "/foo")
    public String foo(){

        return foo;
    }
}
