package top.wikl.component.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.component.sentinel.service.SentinelService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XYL
 * @version 1.2
 * @since 2022/2/19 0019 17:31
 */
@RequestMapping(value = "/sentinel")
@RestController
public class SentinelController {

    @Resource
    private SentinelService sentinelService;

    @GetMapping(value = "/test")
    public Map<String, Object> testSentinel() {
        final String sentinel = sentinelService.testSentinel();

        final HashMap<String, Object> map = new HashMap<>(1);

        map.put("msg", sentinel);

        return map;
    }
}
