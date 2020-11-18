package top.wikl.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 从配置重新读取配置
 *
 * @author XYL
 * @version 1.2
 * @since 2020/11/18 13:32
 */
@RequestMapping("/trace")
@RestController
public class ConfigController {

    @Value("${foo}")
    private String foo;

    @GetMapping(value = "/config")
    public String config() {

        return foo;
    }
}
