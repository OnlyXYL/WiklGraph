package top.wikl.component.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.wikl.component.model.SearchGraphInfoInput;
import top.wikl.component.service.LogOtherService;
import top.wikl.component.service.LogService;

import javax.annotation.Resource;

/**
 * @author XYL
 * @title: LogTest
 * @description: TODO
 * @date 2020/5/27 14:49
 * @return
 * @since V1.0
 */
@RequestMapping("/log")
@RestController
public class LogTest {

    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Resource
    private LogService logService;

    @Resource
    private LogOtherService logOtherService;

    /**
     * 测试日志输出
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/27 16:32
     * @since V1.1
     */
    @ApiOperation(value = "测试日志输出", notes = "测试日志输出")
    @GetMapping("/get/{param}")
    public ResponseEntity<?> log(@PathVariable("param") String param) {

        try {
            // 打印日志
            logService.test(param);

            logOtherService.test_1(param);
        } catch (Exception e) {
        }

        return ResponseEntity.ok().build();
    }

    /**
     * 测试post方式日志输出
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/28 14:31
     * @since V1.1
     */
    @ApiOperation(value = "测试post方式日志输出", notes = "测试post方式日志输出")
    @PostMapping("/post")
    public ResponseEntity<SearchGraphInfoInput> post(@RequestBody SearchGraphInfoInput searchGraphInfoInput) {

        SearchGraphInfoInput graphInfo = logService.graphInfo(searchGraphInfoInput);

        return ResponseEntity
                .ok()
                .body(
                        graphInfo
                );
    }

}
