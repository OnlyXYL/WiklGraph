package top.wikl.neo4j.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.entity.graph.input.InInput;
import top.wikl.entity.graph.output.WiklNodeInfo;
import top.wikl.exception.InputException;
import top.wikl.neo4j.service.Neo4jService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
     * @return org.springframework.http.ResponseEntity<java.util.List<org.neo4j.driver.v1.Record>>
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
}
