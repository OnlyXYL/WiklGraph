package top.wikl.neo4j.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.neo4j.config.properties.TestAutoRefreshProperties;

import javax.annotation.Resource;

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


    @Resource
    private TestAutoRefreshProperties testAutoRefreshProperties;

    @GetMapping(value = "/refresh")
    public String foo(){

        final String format = String.format("姓名：%s，性别：%s，年龄：%s", testAutoRefreshProperties.getName(), testAutoRefreshProperties.getSex(), testAutoRefreshProperties.getAge());

        return format;
    }
}
