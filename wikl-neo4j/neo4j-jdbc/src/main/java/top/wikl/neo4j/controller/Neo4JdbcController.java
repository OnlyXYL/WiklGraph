package top.wikl.neo4j.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.wikl.entity.graph.input.InInput;
import top.wikl.entity.graph.output.WiklNodeInfo;
import top.wikl.exception.InputException;
import top.wikl.neo4j.service.Neo4jService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XYL
 * @title: Neo4JdbcController
 * @description: Neo4j Jdbc
 * @date 2019/10/30 19:53
 * @return
 * @since V1.0
 */
@Api(tags = {"Neo4j Jdbc"})
@RequestMapping(value = "/neo4j/jdbc")
@RestController
public class Neo4JdbcController {

    @Resource
    private Neo4jService neo4jService;

    /**
     * 查询单个点
     *
     * @param inInput, bindingResult
     * @return org.springframework.http.ResponseEntity<java.util.List < org.neo4j.driver.v1.Record>>
     * @author XYL
     * @date 2019/10/31 11:38
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "查询单个点返回结果")})
    @ApiOperation(value = "查询单个点", notes = "查询单个点")
    @PostMapping(value = "/search/node/one")
    public ResponseEntity<List<WiklNodeInfo>> searchOneNode(@RequestBody @Valid InInput inInput, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        List<WiklNodeInfo> nodeInfos = neo4jService.searchOneNode(inInput);

        return ResponseEntity
                .ok()
                .body(
                        nodeInfos
                );
    }

    @GetMapping("/create")
    public  ResponseEntity<?> createNode(){

        HashMap<String, Object> params = new HashMap<>();
        params.put("name","里斯");
        params.put("age","20");
        params.put("sex","女");

        neo4jService.createNode(params);

        return ResponseEntity
                .ok()
               .build();
    }

    @GetMapping("/create/batch")
    public  ResponseEntity<?> createNodes(){

        HashMap<String, Object> xiaohong = new HashMap<>();
        xiaohong.put("name","小红");
        xiaohong.put("age","20");
        xiaohong.put("sex","女");
        xiaohong.put("teacher","王老师");
        xiaohong.put("vLabel","Student");

        HashMap<String, Object> xiaoming = new HashMap<>();
        xiaoming.put("name","小明");
        xiaoming.put("age","20");
        xiaoming.put("sex","男");
        xiaoming.put("teacher","李老师");
        xiaoming.put("vLabel","Student");

        HashMap<String, Object> xiaohui = new HashMap<>();
        xiaohui.put("name","小灰");
        xiaohui.put("age","20");
        xiaohui.put("sex","男");
        xiaohui.put("teacher","韩老师");
        xiaohui.put("vLabel","Student");

        HashMap<String, Object> wanglaoshi = new HashMap<>();
        wanglaoshi.put("name","王老师");
        wanglaoshi.put("age","20");
        wanglaoshi.put("sex","男");
        wanglaoshi.put("vLabel","Teacher");

        HashMap<String, Object> lilaoshi = new HashMap<>();
        lilaoshi.put("name","李老师");
        lilaoshi.put("age","20");
        lilaoshi.put("sex","男");
        lilaoshi.put("vLabel","Teacher");

        HashMap<String, Object> hanlaoshi = new HashMap<>();
        hanlaoshi.put("name","韩老师");
        hanlaoshi.put("age","20");
        hanlaoshi.put("sex","男");
        hanlaoshi.put("vLabel","Teacher");

        List<Map> start = new ArrayList<>();
        start.add(xiaohong);
        start.add(xiaoming);
        start.add(xiaohui);

        List<Map> end = new ArrayList<>();
        end.add(wanglaoshi);
        end.add(lilaoshi);
        end.add(hanlaoshi);

        neo4jService.createNodes(start,end);

        return ResponseEntity
                .ok()
                .build();
    }
}
