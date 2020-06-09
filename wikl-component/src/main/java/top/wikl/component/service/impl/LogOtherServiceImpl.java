package top.wikl.component.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wikl.annotion.WiklMethodLog;
import top.wikl.component.service.LogOtherService;
import top.wikl.enums.log.LogLevel;

/**
 * @author XYL
 * @title: LogOtherServiceImpl
 * @description: TODO
 * @date 2020/5/27 17:25
 * @return
 * @since V1.0
 */
@Slf4j
@Service
public class LogOtherServiceImpl implements LogOtherService {

    @WiklMethodLog(description = "属性日志信息 test_1",level = LogLevel.ERROR)
    @Override
    public String test_1(String params) {

        return "第二个service";
    }
}
