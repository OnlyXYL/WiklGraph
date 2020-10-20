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

    /**
     * 批量插入
     *
     * @param
     * @return org.springframework.http.ResponseEntity<?>
     * @author XYL
     * @since 18:31 2020/10/20
     **/
    @GetMapping("/batch/insert")
    public ResponseEntity<?> batchInsertNode(){

        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>(5);

        HashMap<String, Object> yuangong = new HashMap<>(5);
        yuangong.put("name","员工");
        yuangong.put("graphId",947);
        yuangong.put("label","v_03ba18b251764794ac5d1b4634b4d232");
        yuangong.put("instanceLabel","v_03ba18b251764794ac5d1b4634b4d232");
        yuangong.put("id",4668);
        yuangong.put("p_suoshugongsi_48","所属公司");
        yuangong.put("p_xingbie_57","性别");
        yuangong.put("p_xingming_64","姓名");
        yuangong.put("p_nianling_55","年龄");
        yuangong.put("p_suoshuguojia_67","所属国家");
        yuangong.put("p_chushengriqi_35","出生日期");

        HashMap<String, Object> gongsi = new HashMap<>(5);
        gongsi.put("name","公司");
        gongsi.put("graphId",947);
        gongsi.put("label","v_0ac999ea78324269a2df6a97c2e85585");
        gongsi.put("instanceLabel","v_0ac999ea78324269a2df6a97c2e85585");
        gongsi.put("id",4669);
        gongsi.put("p_didian_33","地点");
        gongsi.put("p_suozaidi_33","所在地");
        gongsi.put("p_zhuceshijian_33","注册时间");
        gongsi.put("p_mingcheng_267","名称");

        HashMap<String, Object> chengshi = new HashMap<>(5);
        chengshi.put("name","城市");
        chengshi.put("graphId",947);
        chengshi.put("label","v_e86f4dddd7ae43fe88d2365af9032895");
        chengshi.put("instanceLabel","v_e86f4dddd7ae43fe88d2365af9032895");
        chengshi.put("id",4670);
        chengshi.put("p_mingcheng_268","名称");
        chengshi.put("p_suoshuguojia_68","所属国家");


        HashMap<String, Object> guojia = new HashMap<>(5);
        guojia.put("name","国家");
        guojia.put("graphId",947);
        guojia.put("label","v_cd083624d59147cda51a4d76a27ff00d");
        guojia.put("instanceLabel","v_cd083624d59147cda51a4d76a27ff00d");
        guojia.put("id",4671);
        guojia.put("p_suozaizhou_32","所在洲");
        guojia.put("p_mingcheng_269","名称");

        HashMap<String, Object> zhou = new HashMap<>(5);
        zhou.put("name","洲");
        zhou.put("graphId",947);
        zhou.put("label","v_747e113524c946eb99fe062685974a55");
        zhou.put("instanceLabel","v_747e113524c946eb99fe062685974a55");
        zhou.put("id",4672);
        zhou.put("p_mingcheng_270","名称");

        nodes.add(yuangong);
        nodes.add(gongsi);
        nodes.add(chengshi);
        nodes.add(guojia);
        nodes.add(zhou);

        String label = "Concept1";

        String constratint_name = label+"_name";

        neo4jService.createConstraint(constratint_name,label);

        neo4jService.createNodesUnwind(nodes,label);

        return ResponseEntity
                .ok()
                .build();
    }
}
