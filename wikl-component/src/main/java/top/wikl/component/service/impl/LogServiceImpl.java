package top.wikl.component.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wikl.annotion.WiklMethodLog;
import top.wikl.component.model.SearchGraphInfoInput;
import top.wikl.component.service.LogService;
import top.wikl.enums.log.LogLevel;

/**
 * @author XYL
 * @title: LogServiceImpl
 * @description: TODO
 * @date 2020/5/27 15:54
 * @return
 * @since V1.0
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @WiklMethodLog(description = "属性日志信息 test", level = LogLevel.DEBUG)
    @Override
    public String test(String params) {

        int i = 1 / 0;

        return "第一个个service";
    }

    @WiklMethodLog(description = "post 方式验证日志输出参数", level = LogLevel.DEBUG)
    @Override
    public SearchGraphInfoInput graphInfo(SearchGraphInfoInput searchGraphInfoInput) {

        return new SearchGraphInfoInput(1, "我的图谱");
    }
}
