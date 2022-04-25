package top.wikl.component.sentinel.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;
import top.wikl.component.sentinel.fallback.SentinelServiceFallback;
import top.wikl.component.sentinel.service.SentinelService;

/**
 * @author XYL
 * @version 1.2
 * @since 2022/2/19 0019 17:33
 */
@Service
public class SentinelServiceImpl implements SentinelService {

    @SentinelResource(value = "testSentinel", fallbackClass = SentinelServiceFallback.class)
    @Override
    public String testSentinel() {

        final String format = String.format("这是方法调用，失败的时候会进行降级处理！！！！！！");

        return format;
    }
}
